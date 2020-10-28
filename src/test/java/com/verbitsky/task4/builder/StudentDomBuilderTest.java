package com.verbitsky.task4.builder;

import com.verbitsky.task4.builder.impl.StudentDomBuilder;
import com.verbitsky.task4.entity.Student;
import com.verbitsky.task4.exception.StudentXmlException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

public class StudentDomBuilderTest {
    private String wrongXml = "data/wrongstudents.xml";
    private String correctXml = "data/singleStudent.xml";
    private String bigCorrectXml = "data/students.xml";
    private String xsd = "data/student.xsd";
    private StudentDomBuilder builder;
    private Student student;


    @BeforeClass
    public void setUp() {
        builder = new StudentDomBuilder();
        student = new Student();
        student.setLogin("student1");
        student.setFaculty("ai");
        student.setName("StudentName1");
        student.setPhone(1000001);
        student.getAddress().setCountry("country1");
        student.getAddress().setCity("city1");
        student.getAddress().setStreet("street1");

    }

    @AfterClass
    public void tearDown() {
        builder = null;
        student = null;
    }

    @Test
    public void testBuildStudentSetPositive() throws StudentXmlException {
        Student expected = student;
        builder.buildStudentsSet(correctXml, xsd);
        Set<Student> students = builder.getStudents();
        Assert.assertTrue(students.contains(expected));
    }

    @Test
    public void testBuildStudentSetPositiveBigXmlFile() throws StudentXmlException {
        builder.buildStudentsSet(bigCorrectXml, xsd);
        Set<Student> students = builder.getStudents();
        int expected = 20;
        Assert.assertEquals(expected, students.size());
    }

    @Test(expectedExceptions = StudentXmlException.class)
    public void testBuildStudentSetNegativeWithNullArgs() throws StudentXmlException {
        Student expected = student;
        builder.buildStudentsSet(correctXml, null);
        Set<Student> students = builder.getStudents();
        Assert.assertTrue(students.contains(expected));
    }

    @Test(expectedExceptions = StudentXmlException.class)
    public void testBuildStudentSetNegativeWrongFile() throws StudentXmlException {
        Student expected = student;
        builder.buildStudentsSet(wrongXml, xsd);
        Set<Student> students = builder.getStudents();
        Assert.assertTrue(students.contains(expected));
    }
}