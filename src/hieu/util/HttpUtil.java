package hieu.util;

import javax.net.ssl.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
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
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = getTrustManager();
            sslContext.init(null, trustManagers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            URL input = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) input.openConnection();
            return connection.getInputStream();
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, "InputStream HttpUtil: " + e.getMessage(), e);
        } catch (KeyManagementException e) {
            Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, "InputStream HttpUtil: " + e.getMessage(), e);
        } catch (SSLHandshakeException e) {
            System.out.println("SSL error at: " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static TrustManager[] getTrustManager() {
        TrustManager[] certs = new TrustManager[]{new X509TrustManager() {
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
        if (result != null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(result, StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("src") || line.contains("href")) {
                    line = line.replace("&", "&amp;");
                }
                sb.append(line);
            }
            br.close();
            return sb.toString();
        }
        return null;
    }

    public static StreamSource preProcessInputStream(String url, String fromPart, String toPart) throws IOException {
        String result = convertHttpResultToString(url);
        if (result != null) {
            StringBuffer sb = new StringBuffer();
            try {
                result = result.substring(result.indexOf(fromPart), result.indexOf(toPart));
            } catch (StringIndexOutOfBoundsException ex) {
//            Logger.getLogger(HttpUtil.class.getName()).log(Level.SEVERE, "Substring error at: " + url, ex);
                System.out.println("Substring error at: " + url + ", from: " + fromPart + ", to: " + toPart);
            }

            result = VietnameseEncoder.encode(result);
            result = VietnameseEncoder.decode(result);
            result = StringHelper.refineHTML(result);

            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
            sb.append(result);
            sb.append("</html>");

            InputStream is = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
            return new StreamSource(is);
        }
        return null;
    }

}
