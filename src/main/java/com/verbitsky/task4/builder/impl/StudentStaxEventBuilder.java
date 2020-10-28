package com.verbitsky.task4.builder.impl;

import com.verbitsky.task4.builder.AbstractStudentBuilder;
import com.verbitsky.task4.entity.Student;
import com.verbitsky.task4.exception.StudentXmlException;
import com.verbitsky.task4.util.Helper;
import com.verbitsky.task4.validator.BaseXmlValidator;
import com.verbitsky.task4.xmltag.StudentXmlTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StudentStaxEventBuilder extends AbstractStudentBuilder {
    private static final String DEFAULT_FACULTY = "ai";
    private static Logger logger = LogManager.getLogger();
    private Student student;
    private XMLInputFactory inFactory;
    private XMLEventReader reader;

    public StudentStaxEventBuilder() {
        super();
        inFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildStudentsSet(String xmlPath, String xsdPath) throws StudentXmlException {
        BaseXmlValidator validator = new BaseXmlValidator();
        if (validator.validateXml(xmlPath, xsdPath)) {
            String realXmlPath = Helper.getRealPath(xmlPath);
            try {
                reader = inFactory.createXMLEventReader(new FileInputStream(realXmlPath));
                while (reader.hasNext()) {
                    XMLEvent event = reader.nextEvent();
                    if (event.isStartElement()) {
                        StartElement startElement = event.asStartElement();
                        if (startElement.getName().getLocalPart().equals(StudentXmlTag.STUDENT.getValue())) {
                            student = new Student();
                            Attribute login = startElement.getAttributeByName(new QName(StudentXmlTag.LOGIN.getValue()));
                            student.setLogin(login.getValue());
                            Attribute faculty = startElement.getAttributeByName(new QName(StudentXmlTag.FACULTY.getValue()));
                            String facultyValue = faculty.getValue();
                            if (faculty.getValue() == null) {
                                facultyValue = DEFAULT_FACULTY;
                            }
                            student.setFaculty(facultyValue);
                        } else if (startElement.getName().getLocalPart().equals(StudentXmlTag.TELEPHONE.getValue())) {
                            event = reader.nextEvent();
                            student.setPhone(Integer.parseInt(event.asCharacters().getData()));
                        } else if (startElement.getName().getLocalPart().equals(StudentXmlTag.NAME.getValue())) {
                            event = reader.nextEvent();
                            student.setName(event.asCharacters().getData());
                        } else if (event.isStartElement()) {
                            StartElement addressElement = event.asStartElement();
                            if (addressElement.getName().getLocalPart().equals(StudentXmlTag.COUNTRY.getValue())) {
                                event = reader.nextEvent();
                                student.getAddress().setCountry(event.asCharacters().getData());
                            } else if (addressElement.getName().getLocalPart().equals(StudentXmlTag.CITY.getValue())) {
                                event = reader.nextEvent();
                                student.getAddress().setCity(event.asCharacters().getData());
                            } else if (addressElement.getName().getLocalPart().equals(StudentXmlTag.STREET.getValue())) {
                                event = reader.nextEvent();
                                student.getAddress().setStreet(event.asCharacters().getData());
                            }
                        }
                    }
                    if (event.isEndElement()) {
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equals(StudentXmlTag.STUDENT.getValue())) {
                            students.add(student);
                        }
                    }
                }
            } catch (FileNotFoundException | XMLStreamException e) {
                logger.log(Level.FATAL, "Error while parsing xml " + xmlPath, e);
                throw new RuntimeException("Unable to parse file", e);
            }
        } else {
            logger.log(Level.FATAL, "Error while parsing xml " + xmlPath + " cause: xml file is not valid");
            throw new StudentXmlException("Received not valid xml " + xmlPath);
        }
    }
}