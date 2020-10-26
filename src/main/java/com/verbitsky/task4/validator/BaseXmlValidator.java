package com.verbitsky.task4.validator;

import com.verbitsky.task4.exception.CustomXmlException;
import com.verbitsky.task4.handler.StudentErrorHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class BaseXmlValidator {
    private static Logger logger = LogManager.getLogger();

    public boolean validateXml(String xmlPath, String xsdPath) throws CustomXmlException {
        if (xmlPath != null && xsdPath != null) {
            boolean isXmlValid;
            String realXmlPath = getClass().getClassLoader().getResource(xmlPath).getFile();
            String realXsdPath = getClass().getClassLoader().getResource(xsdPath).getFile();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File schemaFile = new File(realXsdPath);
            try {
                Schema schema = factory.newSchema(schemaFile);
                Validator validator = schema.newValidator();
                Source source = new StreamSource(realXmlPath);
                validator.setErrorHandler(new StudentErrorHandler());
                validator.validate(source);
                isXmlValid = true;
                logger.log(Level.INFO, "document " + xmlPath + " is valid");
            } catch (SAXException | IOException e) {
                throw new CustomXmlException("Null argument xml-path or xsd-path");
            }
            return isXmlValid;
        } else {
            throw new CustomXmlException("Null argument xml-path or xsd-path");
        }
    }
}