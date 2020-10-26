package com.verbitsky.task4.builder;

import com.verbitsky.task4.entity.Student;
import com.verbitsky.task4.exception.CustomXmlException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

public class StudentSaxBuilderTest {
    private String wrongXml = "data/wrongstudents.xml";
    private String correctXml = "data/singlStudent.xml";
    private String bigCorrectXml = "data/students.xml";
    private String xsd = "data/student.xsd";
    private StudentSaxBuilder builder;
    private Student student;


    @BeforeClass
    public void setUp() {
        builder = new StudentSaxBuilder();
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
    public void testBuildStudentSetPositive() throws CustomXmlException {
        Student expected = student;
        builder.buildStudentSet(correctXml, xsd);
        Set<Student> students = builder.getStudents();
        Assert.assertTrue(students.contains(expected));
    }

    @Test
    public void testBuildStudentSetPositiveBigXmlFile() throws CustomXmlException {
        builder.buildStudentSet(bigCorrectXml, xsd);
        Set<Student> students = builder.getStudents();
        int expected = 20;
        Assert.assertEquals(expected, students.size());
    }

    @Test(expectedExceptions = CustomXmlException.class)
    public void testBuildStudentSetNegativeWithNullArgs() throws CustomXmlException {
        Student expected = student;
        builder.buildStudentSet(correctXml, null);
        Set<Student> students = builder.getStudents();
        Assert.assertTrue(students.contains(expected));
    }

    @Test(expectedExceptions = CustomXmlException.class)
    public void testBuildStudentSetNegativeWrongFile() throws CustomXmlException {
        Student expected = student;
        builder.buildStudentSet(wrongXml, xsd);
        Set<Student> students = builder.getStudents();
        Assert.assertTrue(students.contains(expected));
    }
}