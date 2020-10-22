package hieu.thread.techbox;

import hieu.constant.GlobalPath;
import hieu.constant.HTMLPortion;
import hieu.crawler.Crawler;
import hieu.dao.CategoriesDAO;
import hieu.dto.CategoryDTO;
import hieu.jaxb.techbox.categories.Categories;
import hieu.jaxb.techbox.categories.Category;
import hieu.thread.BaseThread;
import hieu.util.HashCodeUtil;
import hieu.util.JAXBUtil;

import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryThread extends BaseThread implements Runnable {

    private Crawler crawler;

    public CategoryThread(Crawler crawler) {
        this.crawler = crawler;
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream output = crawler.crawl();
            Categories categories = (Categories) JAXBUtil.unmarshall(crawler.getRealPath() + GlobalPath.TECHBOX_CATEGORY_XSD, output, Categories.class);
            if (categories != null) {
                CategoriesDAO dao = new CategoriesDAO();
                List<Category> categoryList = categories.getCategory();
                for (Category category : categoryList) {
                    String hashName = HashCodeUtil.hashString(category.getName().trim());
                    boolean ifExisted = dao.findByHash(hashName);
                    if (!ifExisted) {
                        dao.insert(new CategoryDTO(category.getName(), category.getUrl(), hashName));
                    }
                    Crawler productListCrawler = new Crawler(crawler.getRealPath(), GlobalPath.TECHBOX_MAX_PAGE_XSL, category.getUrl());
                    productListCrawler.setFromPart(HTMLPortion.FROM_PART_TECHBOX_PRODUCT_LIST);
                    productListCrawler.setToPart(HTMLPortion.TO_PART_TECHBOX_PRODUCT_LIST);
                    ProductListThread thread = new ProductListThread(productListCrawler, hashName);
                    thread.start();
                }
            }
        } catch (TransformerException e) {
            Logger.getLogger(CategoryThread.class.getName()).log(Level.SEVERE, "Techbox CategoryThread: " + e.getMessage(), e);
        } catch (Exception e) {
            Logger.getLogger(CategoryThread.class.getName()).log(Level.SEVERE, "Techbox CategoryThread: " + e.getMessage(), e);
        }
    }
}
