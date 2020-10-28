package com.verbitsky.task4.validator;

import com.verbitsky.task4.exception.StudentXmlException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BaseXmlValidatorTest {

    private String wrongXml = "data/wrongstudents.xml";
    private String correctXml = "data/singleStudent.xml";
    private String xsd = "data/student.xsd";
    BaseXmlValidator validator;


    @BeforeClass
    public void setUp() {
        validator = new BaseXmlValidator();
    }

    @AfterClass
    public void tearDown() {
        validator = null;
    }

    @Test
    void validateXmlPositiveTest() throws StudentXmlException {
        Assert.assertTrue(validator.validateXml(correctXml, xsd));
    }

    @Test(expectedExceptions = StudentXmlException.class)
    void validateXmlNegativeTest() throws StudentXmlException {
        Assert.assertFalse(validator.validateXml(wrongXml, xsd));
    }

    @Test(expectedExceptions = StudentXmlException.class)
    void validateXmlNullArgsTest() throws StudentXmlException {
        Assert.assertFalse(validator.validateXml(null, xsd));
    }
}