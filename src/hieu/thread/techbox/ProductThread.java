package hieu.thread.techbox;

import hieu.constant.GlobalPath;
import hieu.constant.HTMLPortion;
import hieu.crawler.Crawler;
import hieu.entity.ProductEntity;
import hieu.entity.UsageEntity;
import hieu.jaxb.techbox.product.Product;
import hieu.repository.ProductRepository;
import hieu.repository.RoomRepository;
import hieu.thread.BaseThread;
import hieu.util.HashCodeUtil;
import hieu.util.JAXBUtil;
import hieu.util.StringHelper;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class ProductThread extends BaseThread implements Runnable {

    private final Crawler crawler;
    private final String categoryHash;
    private final String categoryUrl;

    public ProductThread(Crawler crawler, String categoryHash, String categoryUrl) {
        this.crawler = crawler;
        this.categoryHash = categoryHash;
        this.categoryUrl = categoryUrl;
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream output = crawler.crawl();
            Product product = (Product) JAXBUtil.unmarshall(crawler.getRealPath() + GlobalPath.TECHBOX_PRODUCT_DETAIL_XSD, output, Product.class);
            if (product != null) {

                float price;
                try {
                    price = Float.parseFloat(product.getPrice().trim());
                } catch (NumberFormatException e) {
                    price = 0;
                }

                setImageLinkCrawler();
                String imageUrl = crawler.crawl().toString("UTF-8").trim();

                setDescriptionCrawler();
                String des = crawler.crawl().toString("UTF-8").trim();

                if (!des.isEmpty()) {

                    if (imageUrl.equals(GlobalPath.TECHBOX_URL)) {
                        crawler.setXslPath(GlobalPath.TECHBOX_PRODUCT_BACKUP_IMAGE_URL_XSL);
                        imageUrl = crawler.crawl().toString("UTF-8").trim();

                        if (imageUrl.contains("../..")) {
                            imageUrl = imageUrl.replace("../..", "");
                            imageUrl = "https://techbox.vn" + imageUrl;
                        }
                    }
                    if (imageUrl.contains(" ")) {
                        imageUrl = StringHelper.URLify(imageUrl);
                    }

                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                    ProductRepository repository = ProductRepository.getInstance();
                    String hashName = HashCodeUtil.hashString(product.getName().trim());
                    boolean isExisted = repository.isExisted(hashName);

                    RoomRepository roomRepo = RoomRepository.getInstance();
                    String[] listRoomId = GlobalPath.MAPPING_USAGE.get(categoryUrl);

                    if (!isExisted) {
                        ProductEntity newProduct = new ProductEntity(hashName, product.getName(), price, product.getWarranty(), des, crawler.getUrl(), imageUrl, categoryHash, currentTime, currentTime);
                        if (repository.insert(newProduct)) {
                            for (String roomId : listRoomId) {
                                roomRepo.insertUsage(new UsageEntity(roomId, hashName));
                            }
                        }
                    } else {
                        ProductEntity updateProduct = new ProductEntity(hashName, product.getName(), price, product.getWarranty(), des, crawler.getUrl(), imageUrl, categoryHash, currentTime);
                        if (repository.update(updateProduct)) {
                            for (String roomId : listRoomId) {
                                if (!roomRepo.isUsageExisted(roomId, hashName)) {
                                    roomRepo.insertUsage(new UsageEntity(roomId, hashName));
                                }
                            }
                        }
                    }
                }

                ProductThread.sleep(TimeUnit.DAYS.toMillis(500));
                synchronized (BaseThread.getInstance()) {
                    while (BaseThread.isSuspended()) {
                        BaseThread.getInstance().wait();
                    }
                }
            }
        } catch (TransformerException e) {
            System.out.println("Transforming error at Techbox: " + crawler.getUrl());
        } catch (InterruptedException e) {
            System.out.println("Techbox Category thread: " + getInstance().getId());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            System.out.println("Unmarshalling error at Techbox: " + crawler.getUrl());
        } catch (SAXException e) {
            System.out.println("Parsing error at Techbox: " + crawler.getUrl());
        }
    }

    private void setImageLinkCrawler() {
        crawler.setXslPath(GlobalPath.TECHBOX_PRODUCT_IMAGE_URL_XSL);
        crawler.setFromPart(HTMLPortion.FROM_PART_TECHBOX_PRODUCT_IMAGE);
        crawler.setToPart(HTMLPortion.TO_PART_TECHBOX_PRODUCT_IMAGE);
    }

    private void setDescriptionCrawler() {
        crawler.setXslPath(GlobalPath.TECHBOX_PRODUCT_DESCRIPTION_XSL);
        crawler.setFromPart(HTMLPortion.FROM_PART_TECHBOX_PRODUCT_DESCRIPTION);
        crawler.setToPart(HTMLPortion.TO_PART_TECHBOX_PRODUCT_DESCRIPTION);
    }
}
