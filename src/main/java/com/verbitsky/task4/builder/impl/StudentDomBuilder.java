package com.verbitsky.task4.builder.impl;

import com.verbitsky.task4.builder.AbstractStudentBuilder;
import com.verbitsky.task4.entity.Address;
import com.verbitsky.task4.entity.Student;
import com.verbitsky.task4.exception.StudentXmlException;
import com.verbitsky.task4.handler.StudentErrorHandler;
import com.verbitsky.task4.validator.BaseXmlValidator;
import com.verbitsky.task4.xmltag.StudentXmlTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

public class StudentDomBuilder extends AbstractStudentBuilder {
    private static final String DEFAULT_FACULTY = "ai";
    private static Logger logger = LogManager.getLogger();

    private DocumentBuilder documentBuilder;

    public StudentDomBuilder() {
        super();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            this.documentBuilder = factory.newDocumentBuilder();
            documentBuilder.setErrorHandler(new StudentErrorHandler());
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "Error while getting new document builder from factory");
            throw new RuntimeException("Unable to create StudentDomBuilder",e);
        }
    }

    public Set<Student> getStudents() {
        return students;
    }

    @Override
    public void buildStudentsSet(String xmlPath, String xsdPath) throws StudentXmlException {
        BaseXmlValidator validator = new BaseXmlValidator();
        if (validator.validateXml(xmlPath, xsdPath)) {
            String realXmlPath = getClass().getClassLoader().getResource(xmlPath).getFile();
            try {
                Document document = documentBuilder.parse(realXmlPath);
                Element root = document.getDocumentElement();
                NodeList studentsList = root.getElementsByTagName(StudentXmlTag.STUDENT.getValue());
                for (int i = 0; i < studentsList.getLength(); i++) {
                    Element studentElement = (Element) studentsList.item(i);
                    Student student = buildStudent(studentElement);
                    students.add(student);
                }
            } catch (SAXException | IOException e) {
                logger.log(Level.FATAL, "Error while building students list from xml", e);
                throw new RuntimeException("Unable to parse xmlfile " + xmlPath);
            }
        } else {
            logger.log(Level.ERROR, "Error while parsing xml " + xmlPath + " cause: xml file is not valid");
            throw new StudentXmlException("Received not valid xml " + xmlPath);
        }
    }

    private Student buildStudent(Element studentElement) {
        Student student = new Student();
        if (!(studentElement == null)) {
            String faculty = studentElement.getAttribute(StudentXmlTag.FACULTY.getValue());
            if (faculty == null) {
                faculty = DEFAULT_FACULTY;
            }
            student.setFaculty(faculty);
            student.setLogin(studentElement.getAttribute(StudentXmlTag.LOGIN.getValue()));
            student.setName(getElementContext(studentElement, StudentXmlTag.NAME.getValue()));
            int phone = Integer.parseInt(getElementContext(studentElement, StudentXmlTag.TELEPHONE.getValue()));
            student.setPhone(phone);
            Address address = student.getAddress();
            Element studentAddress = (Element) studentElement.getElementsByTagName(StudentXmlTag.ADDRESS.getValue()).item(0);
            address.setCountry(getElementContext(studentAddress, StudentXmlTag.COUNTRY.getValue()));
            address.setCity(getElementContext(studentAddress, StudentXmlTag.CITY.getValue()));
            address.setStreet(getElementContext(studentAddress, StudentXmlTag.STREET.getValue()));
            return student;
        } else {
            logger.log(Level.ERROR, "Error while building student from Element");
        }
        return student;
    }

    private String getElementContext(Element studentElement, String elementTagName) {
        NodeList nodeList = studentElement.getElementsByTagName(elementTagName);
        Node node = nodeList.item(0);
        return node.getTextContent();
    }
}