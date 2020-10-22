package hieu.thread.boba;

import hieu.constant.GlobalPath;
import hieu.constant.HTMLPortion;
import hieu.crawler.Crawler;
import hieu.jaxb.boba.categories.Categories;
import hieu.jaxb.boba.categories.Category;
import hieu.thread.BaseThread;
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
            Categories categories = (Categories) JAXBUtil.unmarshall(crawler.getRealPath() + GlobalPath.BOBA_CATEGORY_XSD, output, Categories.class);
            if (categories != null) {
                List<Category> categoryList = categories.getCategory();
                for (Category category : categoryList) {
                    Crawler productListCrawler = new Crawler(crawler.getRealPath(), GlobalPath.BOBA_MAX_PAGE_XSL, category.getUrl());
                    productListCrawler.setFromPart(HTMLPortion.FROM_PART_BOBA_PRODUCT_LIST);
                    productListCrawler.setToPart(HTMLPortion.TO_PART_BOBA_PRODUCT_LIST);
                }
            }
        } catch (TransformerException e) {
           Logger.getLogger(CategoryThread.class.getName()).log(Level.SEVERE, "Boba CategoryThread: " + e.getMessage(), e);
        }
    }
}
