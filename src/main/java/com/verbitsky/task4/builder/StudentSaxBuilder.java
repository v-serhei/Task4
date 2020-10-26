package com.verbitsky.task4.builder;

import com.verbitsky.task4.entity.Student;
import com.verbitsky.task4.exception.CustomXmlException;
import com.verbitsky.task4.handler.StudentContentHandler;
import com.verbitsky.task4.handler.StudentErrorHandler;
import com.verbitsky.task4.validator.BaseXmlValidator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class StudentSaxBuilder {
    private Set<Student> students;
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

    public void buildStudentSet (String xmlPath, String xsdPath) throws CustomXmlException {
        if (validator.validateXml(xmlPath, xsdPath)) {
            String realXmlPath = getClass().getClassLoader().getResource(xmlPath).getFile();
            try {
                reader.parse(realXmlPath);
            } catch (SAXException | IOException e) {
               throw new CustomXmlException("Error while parsing file"+xmlPath);
            }
            students = contentHandler.getStudents();
        }else {
            throw new CustomXmlException("Received not valid xml "+xmlPath);
        }
    }
}
