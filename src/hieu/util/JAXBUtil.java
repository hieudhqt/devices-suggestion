package hieu.util;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JAXBUtil {

    public static boolean validateXMLParsedFromHTML(String xsdPath, ByteArrayOutputStream crawledXML) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            InputStream is = new ByteArrayInputStream(crawledXML.toByteArray());
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(is));
            return true;
        } catch (SAXException | IOException e) {
            return false;
        }
    }

    public static Object unmarshall(String xsdPath, ByteArrayOutputStream crawledXML, Class unmarshallingType) {
        try {
            InputStream is = new ByteArrayInputStream(crawledXML.toByteArray());

            JAXBContext context = JAXBContext.newInstance(unmarshallingType);
            Unmarshaller um = context.createUnmarshaller();

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));

            um.setSchema(schema);

            return um.unmarshal(is);
        } catch (JAXBException e) {
            Logger.getLogger(JAXBUtil.class.getName()).log(Level.SEVERE, null, e);
        } catch (SAXException e) {
            Logger.getLogger(JAXBUtil.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;

    }

}
