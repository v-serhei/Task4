package com.verbitsky.task4.builder.impl;

import com.verbitsky.task4.builder.AbstractStudentBuilder;
import com.verbitsky.task4.entity.Student;
import com.verbitsky.task4.exception.StudentXmlException;
import com.verbitsky.task4.handler.StudentContentHandler;
import com.verbitsky.task4.handler.StudentErrorHandler;
import com.verbitsky.task4.validator.BaseXmlValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class StudentSaxBuilder extends AbstractStudentBuilder {
    private static Logger logger = LogManager.getLogger();
    private XMLReader reader;
    private StudentContentHandler contentHandler = new StudentContentHandler ();
    private BaseXmlValidator validator;

    public StudentSaxBuilder() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        reader.setContentHandler(contentHandler);
        reader.setErrorHandler(new StudentErrorHandler());
        validator = new BaseXmlValidator();
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void buildStudentsSet(String xmlPath, String xsdPath) throws StudentXmlException {
        if (validator.validateXml(xmlPath, xsdPath)) {
            String realXmlPath = getClass().getClassLoader().getResource(xmlPath).getFile();
            try {
                reader.parse(realXmlPath);
            } catch (SAXException | IOException e) {
                logger.log(Level.FATAL, "Error while parsing xml " + xmlPath, e);
                throw new RuntimeException("Unable to parse file", e);
            }
            students = contentHandler.getStudents();
        }else {
            logger.log(Level.ERROR, "Error while parsing xml "+xmlPath+" cause: xml file is not valid");
            throw new StudentXmlException("Received not valid xml "+xmlPath);
        }
    }
}