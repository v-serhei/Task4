package com.verbitsky.task4.handler;

import com.verbitsky.task4.entity.Student;
import com.verbitsky.task4.xmltag.StudentXmlTag;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class StudentContentHandler extends DefaultHandler {
    private static final String DEFAULT_FACULTY = "ai";
    private Set<Student> students;
    private Student student;
    private StudentXmlTag currentXmlTag;
    private EnumSet<StudentXmlTag> tagWithText;
    private static final String ELEMENT_STUDENT = StudentXmlTag.STUDENT.getValue();

    public StudentContentHandler() {
        students = new HashSet<>();
        tagWithText = EnumSet.range(StudentXmlTag.NAME, StudentXmlTag.STREET);
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (ELEMENT_STUDENT.equals(qName)) {
            //if find open tag 'student' - create new object student and fill student fields
            student = new Student();
            //if complex tag type
            if (attributes.getLength() > 0) {
                String faculty = attributes.getValue(StudentXmlTag.FACULTY.getValue());
                if (faculty == null) {
                    faculty = DEFAULT_FACULTY;
                }
                student.setFaculty(faculty);
                student.setLogin(attributes.getValue(StudentXmlTag.LOGIN.getValue()));
            }
            //if simple tag type - read tag body
        } else {
            StudentXmlTag buf = StudentXmlTag.valueOf(qName.toUpperCase());
            if (tagWithText.contains(buf)) {
                currentXmlTag = buf;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        //if find close tag 'student' - add created student to set
        if (ELEMENT_STUDENT.equals(qName)) {
            students.add(student);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        //read content from tag body
        String data = new String(ch, start, length);
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case NAME: {
                    student.setName(data);
                    break;
                }
                case TELEPHONE: {
                    student.setPhone(Integer.parseInt(data));
                    break;
                }
                case STREET: {
                    student.getAddress().setStreet(data);
                    break;
                }
                case CITY: {
                    student.getAddress().setCity(data);
                    break;
                }
                case COUNTRY: {
                    student.getAddress().setCountry(data);
                    break;
                }
            }
        }
        currentXmlTag = null;
    }

    public Set<Student> getStudents() {
        return students;
    }
}