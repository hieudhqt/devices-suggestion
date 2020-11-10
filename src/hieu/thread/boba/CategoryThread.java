package hieu.thread.boba;

import hieu.constant.GlobalPath;
import hieu.constant.HTMLPortion;
import hieu.crawler.Crawler;
import hieu.entity.CategoryEntity;
import hieu.jaxb.boba.categories.Categories;
import hieu.jaxb.boba.categories.Category;
import hieu.repository.CategoryRepository;
import hieu.thread.BaseThread;
import hieu.util.HashCodeUtil;
import hieu.util.JAXBUtil;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CategoryThread extends BaseThread implements Runnable {

    private final Crawler crawler;

    public CategoryThread(Crawler crawler) {
        this.crawler = crawler;
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream output = crawler.crawl();
            Categories categories = (Categories) JAXBUtil.unmarshall(crawler.getRealPath() + GlobalPath.BOBA_CATEGORY_XSD, output, Categories.class);
            if (categories != null) {
                List<Category> categoryList = categories.getCategory();
                CategoryRepository repository = CategoryRepository.getInstance();
                List<String> excludeList = Arrays.asList(GlobalPath.EXCLUDE_LIST);
                for (Category category : categoryList) {
                    String refineUrl = category.getUrl().replace("/#", "");
                    if (!excludeList.contains(refineUrl)) {
                        String hashName = HashCodeUtil.hashString(category.getName().trim());
                        boolean isExisted = repository.isExisted(hashName);
                        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                        if (!isExisted) {
                            repository.insert(new CategoryEntity(hashName, category.getName(), refineUrl, currentTime, currentTime));
                        }

                        Crawler productListCrawler = new Crawler(crawler.getRealPath(), GlobalPath.BOBA_MAX_PAGE_XSL, refineUrl);
                        productListCrawler.setFromPart(HTMLPortion.FROM_PART_BOBA_PRODUCT_LIST);
                        productListCrawler.setToPart(HTMLPortion.TO_PART_BOBA_PRODUCT_LIST);
                        ProductListThread thread = new ProductListThread(productListCrawler, hashName);
                        thread.start();
                    }

                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                }

                CategoryThread.sleep(TimeUnit.DAYS.toMillis(500));
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            }
        } catch (TransformerException e) {
            System.out.println("Transforming error at Boba: " + crawler.getUrl());
        } catch (InterruptedException e) {
            System.out.println("Baba Category thread: " + getInstance().getId());
        } catch (JAXBException e) {
            System.out.println("Unmarshalling error at Boba: " + crawler.getUrl());
        } catch (SAXException e) {
            System.out.println("Parsing error at Boba: " + crawler.getUrl());
        }
    }
}
