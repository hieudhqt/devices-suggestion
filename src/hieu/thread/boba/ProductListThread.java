package hieu.thread.boba;

import hieu.crawler.Crawler;
import hieu.thread.BaseThread;

public class ProductListThread extends BaseThread implements Runnable {

    private Crawler crawler;
    private String categoryHash;

    public ProductListThread(Crawler crawler, String categoryHash) {
        this.crawler = crawler;
        this.categoryHash = categoryHash;
    }

    @Override
    public void run() {
    }
}
