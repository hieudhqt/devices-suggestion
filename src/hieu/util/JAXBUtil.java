package hieu.util;

import hieu.constant.GlobalPath;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

public class JAXBUtil {

    public static Object unmarshall(String xsdPath, ByteArrayOutputStream crawledXML, Class unmarshallingType) throws JAXBException, SAXException {
        InputStream is = new ByteArrayInputStream(crawledXML.toByteArray());

        JAXBContext context = JAXBContext.newInstance(unmarshallingType);
        Unmarshaller um = context.createUnmarshaller();

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(xsdPath));

        um.setSchema(schema);

        return um.unmarshal(is);
    }

    public static <T> ByteArrayOutputStream marshall(T object, Class<T> marshallingType) throws JAXBException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        JAXBContext context = JAXBContext.newInstance(marshallingType);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(object, outputStream);

        return outputStream;
    }

    public static void convertToPDF(InputStream is, String realPath, String xslPath, String username) throws IOException, SAXException, TransformerException {
        Source source = new StreamSource(is);

        File configFile = new File(realPath + GlobalPath.PDF_CONFIG);
        FopFactory fopFactory = FopFactory.newInstance(configFile);
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        OutputStream out = new FileOutputStream("E:\\Google Drive\\Documents\\Summer 2020\\PRX301\\Code\\final-project\\web\\report-" + username + ".pdf");

        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(realPath + xslPath));

            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(source, res);
        } finally {
            out.close();
        }
    }
}
