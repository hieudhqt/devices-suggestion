package hieu.util;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpUtil {

    private static InputStream getHttp(String url) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = getTrustManager();
            sslContext.init(null, trustManagers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            URL input = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) input.openConnection();
            return connection.getInputStream();
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, "InputStream HttpUtil: " + e.getMessage(), e);
        } catch (KeyManagementException e) {
            Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, "InputStream HttpUtil: " + e.getMessage(), e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TrustManager[] getTrustManager() {
        TrustManager[] certs = new TrustManager[] {new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};
        return certs;
    }

    private static String convertHttpResultToString(String url) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStream result = getHttp(url);
        BufferedReader br = new BufferedReader(new InputStreamReader(result, StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("src") || line.contains("href")) {
                line = line.replace("&", "&amp;");
            }
            line = line.replace("&reg;", "&#174;")
                    .replace("&hellip;", "")
                    .replace("&nbsp;", "");
//                    .replace("&#38;nbsp;", "");
            line = VietnameseEncoder.encode(line);
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public static StreamSource preProcessInputStream(String url, String fromPart, String toPart) throws IOException {
        String result = convertHttpResultToString(url);
        StringBuffer sb = new StringBuffer();
        try {
            result = result.substring(result.indexOf(fromPart), result.indexOf(toPart));
        } catch (StringIndexOutOfBoundsException ex) {
            Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, "Substring error at: " + url, ex);
        }

//        result = VietnameseEncoder.decode(result);
        result = VietnameseEncoder.encode(result);

        result = StringHelper.refineHTML(result);
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        sb.append(result);
        sb.append("</html>");

        if (url.equals("https://techbox.vn/camera-ip-ngoai-troi-vitacam-vb720ii.html")) {
            BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\Google Drive\\Documents\\Summer 2020\\PRX301\\Code\\final-project\\web\\WEB-INF\\techbox\\crawl.xml"));
            bw.write(sb.toString());
            bw.close();
        }

        InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        return new StreamSource(is);
    }

}
