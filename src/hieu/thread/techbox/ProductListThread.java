package hieu.thread.techbox;

import hieu.constant.GlobalPath;
import hieu.constant.HTMLPortion;
import hieu.crawler.Crawler;
import hieu.thread.BaseThread;

import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductListThread extends BaseThread implements Runnable {

    private Crawler crawler;
    private String categoryHash;

    public ProductListThread(Crawler crawler, String categoryHash) {
        this.crawler = crawler;
        this.categoryHash = categoryHash;
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream output = crawler.crawl();
            String currentUrl = crawler.getUrl();
            int maxPage;
            try {
                maxPage = Integer.parseInt(output.toString());
            } catch (NumberFormatException e) {
                maxPage = 1;
            }
            if (crawler.getUrl().contains(GlobalPath.TECHBOX_URL)) {
                crawler.setXslPath(GlobalPath.TECHBOX_PRODUCT_LINK_XSL);
                for (int i = 1; i <= maxPage; i++) {
                    crawler.setUrl(currentUrl + "?page=" + i);
                    String productListUrl = crawler.crawl().toString();
                    List<String> linkList = Arrays.asList(productListUrl.split("/"));
                    for (String productUrl : linkList) {
                        if (!productUrl.isEmpty()) {
                            Crawler productCrawler = new Crawler(crawler.getRealPath(), GlobalPath.TECHBOX_PRODUCT_DETAIL_XSL, "https://techbox.vn/" + productUrl);
                            productCrawler.setFromPart(HTMLPortion.FROM_PART_TECHBOX_PRODUCT_DETAIL);
                            productCrawler.setToPart(HTMLPortion.TO_PART_TECHBOX_PRODUCT_DETAIL);
                            ProductThread thread = new ProductThread(productCrawler, categoryHash);
                            thread.start();
                        }
                    }
                }
            }
        } catch (TransformerException e) {
            Logger.getLogger(ProductListThread.class.getName()).log(Level.SEVERE, "Techbox ProductListThread: " + e.getMessage(), e);
        }
    }
}
