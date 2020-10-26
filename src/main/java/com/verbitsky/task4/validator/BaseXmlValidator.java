package com.verbitsky.task4.validator;

import com.verbitsky.task4.handler.StudentErrorHandler;
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
    private boolean valid;

    public boolean isDocumentValid(String xmlPath, String xsdPath) {
        String lang = XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(lang);
        File schemaFile = new File(xsdPath);
        try {
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xmlPath);
            validator.setErrorHandler(new StudentErrorHandler());
            validator.validate(source);
            valid= true;
        } catch (SAXException | IOException e) {
            valid = false;
        }
        return valid;
    }
}
