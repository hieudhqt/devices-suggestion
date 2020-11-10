package hieu.thread.boba;

import hieu.constant.GlobalPath;
import hieu.crawler.Crawler;
import hieu.entity.ProductEntity;
import hieu.entity.UsageEntity;
import hieu.jaxb.boba.product.Product;
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
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class ProductThread extends BaseThread implements Runnable {

    private final Crawler crawler;
    private final String categoryHash;

    public ProductThread(Crawler crawler, String categoryHash) {
        this.crawler = crawler;
        this.categoryHash = categoryHash;
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream output = crawler.crawl();
            Product product = (Product) JAXBUtil.unmarshall(crawler.getRealPath() + GlobalPath.BOBA_PRODUCT_DETAIL_XSD, output, Product.class);
            if (product != null) {
                String description = product.getDescription().trim();
                if (!description.isEmpty()) {

                    ProductRepository repository = ProductRepository.getInstance();
                    String hashName = HashCodeUtil.hashString(product.getName().trim());
                    boolean isExisted = repository.isExisted(hashName);

                    float price;
                    try {
                        price = Float.parseFloat(product.getPrice().trim());
                    } catch (NumberFormatException e) {
                        price = 0;
                    }
                    String imageUrl = product.getImageUrl();
                    if (imageUrl.contains(" ")) {
                        imageUrl = StringHelper.URLify(imageUrl);
                    }

                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                    String[] urlPart = crawler.getUrl().trim().split("/");
                    String subCateUrl = "https://boba.vn/" + urlPart[3];

                    RoomRepository roomRepo = RoomRepository.getInstance();
                    String[] listRoomId = GlobalPath.MAPPING_USAGE.get(subCateUrl);
                    if (listRoomId.length == 0) {
                        listRoomId[0] = "other";
                    }

                    if (!isExisted) {
                        ProductEntity newProduct = new ProductEntity(hashName, product.getName(), price, product.getWarranty(), description, crawler.getUrl(), imageUrl, categoryHash, currentTime, currentTime);
                        if (repository.insert(newProduct)) {
                            for (String roomId : listRoomId) {
                                roomRepo.insertUsage(new UsageEntity(roomId, hashName));
                            }
                        }
                    } else {
                        ProductEntity updateProduct = new ProductEntity(hashName, product.getName(), price, product.getWarranty(), description, crawler.getUrl(), imageUrl, categoryHash, currentTime);
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
