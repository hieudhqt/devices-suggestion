package hieu.crawler;

import hieu.util.HttpUtil;
import hieu.util.StringHelper;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Crawler {

    private final String realPath;
    private String xslPath;
    private String url;

    private String fromPart, toPart;

    public String getRealPath() {
        return realPath;
    }

    public String getXslPath() {
        return xslPath;
    }

    public void setXslPath(String xslPath) {
        this.xslPath = this.realPath + xslPath;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setFromPart(String fromPart) {
        this.fromPart = fromPart;
    }

    public void setToPart(String toPart) {
        this.toPart = toPart;
    }

    public Crawler(String realPath, String xslPath, String url) {
        this.realPath = realPath;
        this.xslPath = realPath + xslPath;
        this.url = StringHelper.URLify(url.trim());
    }

    public ByteArrayOutputStream crawl() throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource src = new StreamSource(xslPath);
        Transformer transformer = factory.newTransformer(src);
        factory.setURIResolver(new URIResolver() {
            @Override
            public Source resolve(String href, String base) throws TransformerException {
                try {
                    StreamSource ss = HttpUtil.preProcessInputStream(href, fromPart, toPart);
                    return ss;
                } catch (IOException ex) {
                    Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, "Crawling error at: " + url, ex);
                }
                return null;
            }
        });
        Source source = factory.getURIResolver().resolve(url, "");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        if (source != null) {
            try {
                transformer.transform(source, new StreamResult(output));
            } catch (Exception ex) {
//                Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, "Bug: " + this.getUrl(), ex);
                System.out.println("Bug at: " + this.url);
            }
        }
        return output;
    }

}
