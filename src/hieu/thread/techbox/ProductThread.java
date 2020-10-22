package hieu.thread.techbox;

import hieu.constant.GlobalPath;
import hieu.constant.HTMLPortion;
import hieu.crawler.Crawler;
import hieu.dao.ProductDAO;
import hieu.dto.ProductDTO;
import hieu.jaxb.techbox.product.Product;
import hieu.thread.BaseThread;
import hieu.util.HashCodeUtil;
import hieu.util.JAXBUtil;
import hieu.util.StringHelper;

import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductThread extends BaseThread implements Runnable {

    private Crawler crawler;
    private String categoryHash;

    public ProductThread(Crawler crawler, String categoryHash) {
        this.crawler = crawler;
        this.categoryHash = categoryHash;
    }

    @Override
    public void run() {
        try {
            ByteArrayOutputStream output = crawler.crawl();
            Product product = (Product) JAXBUtil.unmarshall(crawler.getRealPath() + GlobalPath.TECHBOX_PRODUCT_DETAIL_XSD, output, Product.class);
            if (product != null) {
                ProductDAO dao = new ProductDAO();
                String hashName = HashCodeUtil.hashString(product.getName().trim());
                boolean ifExisted = dao.findByHash(hashName);
                if (!ifExisted) {
                    ProductDTO dto = new ProductDTO(hashName, product.getName(), product.getWarranty(), crawler.getUrl(), categoryHash, Float.parseFloat(product.getPrice().trim()));
                    dao.insert(dto);
                    setDescriptionCrawler();
                    String des = crawler.crawl().toString();
                    dto.setDescription(des);
//                    setImageLinkCrawler();
//                    String imageUrl = crawler.crawl().toString();
//                    if (imageUrl.contains(" ")) {
//                        imageUrl = StringHelper.URLify(imageUrl);
//                    }
//                    dto.setImageLink(imageUrl);
                    dao.updateInfo(dto);
//                if (imageUrl.equals(GlobalPath.TECHBOX_URL)) {
//                    crawler
//                }
//                    System.out.println("Url: " + crawler.getUrl() + ", image link: " + imageUrl);
                }
            }
        } catch (TransformerException e) {
            Logger.getLogger(ProductThread.class.getName()).log(Level.SEVERE, "Techbox ProductThread: " + crawler.getUrl(), e);
        } catch (Exception e) {
            Logger.getLogger(ProductThread.class.getName()).log(Level.SEVERE, "Techbox ProductThread: " + crawler.getUrl(), e);
        }
    }

    private void setImageLinkCrawler() {
        crawler.setXslPath(GlobalPath.TECHBOX_PRODUCT_IMAGE_LINK_XSL);
        crawler.setFromPart(HTMLPortion.FROM_PART_TECHBOX_PRODUCT_IMAGE);
        crawler.setToPart(HTMLPortion.TO_PART_TECHBOX_PRODUCT_IMAGE);
    }

    private void setDescriptionCrawler() {
        crawler.setXslPath(GlobalPath.TECHBOX_PRODUCT_DESCRIPTION_XSL);
        crawler.setFromPart(HTMLPortion.FROM_PART_TECHBOX_PRODUCT_DESCRIPTION);
        crawler.setToPart(HTMLPortion.TO_PART_TECHBOX_PRODUCT_DESCRIPTION);
    }
}
