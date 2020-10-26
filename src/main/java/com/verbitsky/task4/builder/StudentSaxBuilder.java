package com.verbitsky.task4.builder;

import com.verbitsky.task4.entity.Student;
import com.verbitsky.task4.handler.StudentContentHandler;
import com.verbitsky.task4.handler.StudentErrorHandler;
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
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void buildStudentSet (String xmlFileName) {
        try {
            reader.parse(xmlFileName);
        } catch (SAXException | IOException e) {
            // TODO: 26.10.2020 log?
            //e.printStackTrace();
        }
        students = contentHandler.getStudents();
    }
}
