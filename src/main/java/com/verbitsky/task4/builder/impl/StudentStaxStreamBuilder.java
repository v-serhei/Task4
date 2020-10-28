package com.verbitsky.task4.builder.impl;

import com.verbitsky.task4.builder.AbstractStudentBuilder;
import com.verbitsky.task4.entity.Address;
import com.verbitsky.task4.entity.Student;
import com.verbitsky.task4.exception.StudentXmlException;
import com.verbitsky.task4.validator.BaseXmlValidator;
import com.verbitsky.task4.xmltag.StudentXmlTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

public class StudentStaxStreamBuilder extends AbstractStudentBuilder {
    private static final String DEFAULT_FACULTY = "ai";
    private static Logger logger = LogManager.getLogger();
    private XMLInputFactory inputFactory;

    public StudentStaxStreamBuilder() {
        super();
        inputFactory = XMLInputFactory.newInstance();
    }

    public Set<Student> getStudents() {
        return students;
    }

    @Override
    public void buildStudentsSet(String xmlPath, String xsdPath) throws StudentXmlException {
        BaseXmlValidator validator = new BaseXmlValidator();
        if (validator.validateXml(xmlPath, xsdPath)) {
            String realXmlPath = getClass().getClassLoader().getResource(xmlPath).getFile();
            XMLStreamReader reader;
            String name;
            try (FileInputStream inputStream = new FileInputStream(new File(realXmlPath))) {
                reader = inputFactory.createXMLStreamReader(inputStream);
                while (reader.hasNext()) {
                    int type = reader.next();
                    if (type == XMLStreamConstants.START_ELEMENT) {
                        name = reader.getLocalName();
                        if (name.equals(StudentXmlTag.STUDENT.getValue())) {
                            Student student = buildStudent(reader);
                            students.add(student);
                        }
                    }
                }
            } catch (IOException | XMLStreamException e) {
                logger.log(Level.FATAL, "Error while read xmlfile " + xmlPath, e);
                throw new RuntimeException("Unable to get XMLStreamReader");
            }
        } else {
            logger.log(Level.ERROR, "Error while parsing xml " + xmlPath + " cause: xml file is not valid");
            throw new StudentXmlException("Received not valid xml " + xmlPath);
        }
    }

    private Student buildStudent(XMLStreamReader reader) throws XMLStreamException, StudentXmlException {
        Student student = new Student();
        student.setLogin(reader.getAttributeValue(null, StudentXmlTag.LOGIN.getValue()));
        String faculty = reader.getAttributeValue(null, StudentXmlTag.FACULTY.getValue());
        if (faculty == null) {
            faculty = DEFAULT_FACULTY;
        }
        student.setFaculty(faculty);

        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT: {
                    name = reader.getLocalName();
                    switch (StudentXmlTag.valueOf(name.toUpperCase())) {
                        case NAME: {
                            student.setName(getXMLText(reader));
                            break;
                        }
                        case TELEPHONE: {
                            student.setPhone(Integer.parseInt(getXMLText(reader)));
                            break;
                        }
                        case ADDRESS: {
                            student.setAddress(getXMLStudentAddress(reader));
                            break;
                        }
                    }
                    break;
                }
                case XMLStreamConstants.END_ELEMENT: {
                    name = reader.getLocalName();
                    if (StudentXmlTag.valueOf(name.toUpperCase()) == StudentXmlTag.STUDENT) {
                        return student;
                    }
                }
            }
        }
        throw new StudentXmlException("Received unknown element in tag \"student\"");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

    private Address getXMLStudentAddress(XMLStreamReader reader) throws XMLStreamException, StudentXmlException {
        Address address = new Address();
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT: {
                    name = reader.getLocalName();
                    switch (StudentXmlTag.valueOf(name.toUpperCase())) {
                        case CITY: {
                            address.setCity(getXMLText(reader));
                            break;
                        }
                        case COUNTRY: {
                            address.setCountry(getXMLText(reader));
                            break;
                        }
                        case STREET: {
                            address.setStreet(getXMLText(reader));
                            break;
                        }
                    }
                    break;
                }
                case XMLStreamConstants.END_ELEMENT: {
                    name = reader.getLocalName();
                    if (StudentXmlTag.valueOf(name.toUpperCase()) == StudentXmlTag.ADDRESS) {
                        return address;
                    }
                }
            }
        }
        throw new StudentXmlException("Received unknown element in tag \"address\"");
    }
}