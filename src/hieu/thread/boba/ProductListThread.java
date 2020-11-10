package hieu.thread.boba;

import hieu.constant.GlobalPath;
import hieu.constant.HTMLPortion;
import hieu.crawler.Crawler;
import hieu.thread.BaseThread;

import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

public class ProductListThread extends BaseThread implements Runnable {

    private final Crawler crawler;
    private final String categoryHash;

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
            if (crawler.getUrl().contains(GlobalPath.BOBA_URL)) {
                crawler.setXslPath(GlobalPath.BOBA_PRODUCT_URL_XSL);
                for (int i = 1; i <= maxPage; i++) {
                    crawler.setUrl(currentUrl + "?p=" + i);
                    String productListUrl = crawler.crawl().toString();
                    String[] linkList = productListUrl.split("}");
                    for (String productUrl : linkList) {
                        if (!productUrl.isEmpty()) {
                            Crawler productCrawler = new Crawler(crawler.getRealPath(), GlobalPath.BOBA_PRODUCT_DETAIL_XSL, "https://boba.vn" + productUrl);
                            productCrawler.setFromPart(HTMLPortion.FROM_PART_BOBA_PRODUCT_LIST);
                            productCrawler.setToPart(HTMLPortion.TO_PART_BOBA_PRODUCT_LIST);
                            ProductThread thread = new ProductThread(productCrawler, categoryHash);
                            thread.start();

                            synchronized (BaseThread.getInstance()) {
                                while (BaseThread.isSuspended()) {
                                    BaseThread.getInstance().wait();
                                }
                            }
                        }
                    }
                }

                ProductListThread.sleep(TimeUnit.DAYS.toMillis(500));
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
        }
    }
}
