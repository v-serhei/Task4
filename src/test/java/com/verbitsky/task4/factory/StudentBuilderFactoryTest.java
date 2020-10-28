package com.verbitsky.task4.factory;

import com.verbitsky.task4.builder.AbstractStudentBuilder;
import com.verbitsky.task4.builder.impl.StudentDomBuilder;
import com.verbitsky.task4.builder.impl.StudentSaxBuilder;
import com.verbitsky.task4.builder.impl.StudentStaxEventBuilder;
import com.verbitsky.task4.builder.impl.StudentStaxStreamBuilder;
import com.verbitsky.task4.exception.StudentXmlException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StudentBuilderFactoryTest {

    @Test
    public void testCreateStudentBuilderPositiveDomBuilder() throws StudentXmlException {
        AbstractStudentBuilder actual = StudentBuilderFactory.createStudentBuilder(StudentBuilderType.DOM);
        Assert.assertTrue(actual instanceof StudentDomBuilder);
    }

    @Test
    public void testCreateStudentBuilderPositiveSaxBuilder() throws StudentXmlException {
        AbstractStudentBuilder actual = StudentBuilderFactory.createStudentBuilder(StudentBuilderType.SAX);
        Assert.assertTrue(actual instanceof StudentSaxBuilder);
    }

    @Test
    public void testCreateStudentBuilderPositiveStaxStreamBuilder() throws StudentXmlException {
        AbstractStudentBuilder actual = StudentBuilderFactory.createStudentBuilder(StudentBuilderType.STAX_STREAM);
        Assert.assertTrue(actual instanceof StudentStaxStreamBuilder);
    }

    @Test
    public void testCreateStudentBuilderPositiveStaxEventBuilder() throws StudentXmlException {
        AbstractStudentBuilder actual = StudentBuilderFactory.createStudentBuilder(StudentBuilderType.STAX_EVENT);
        Assert.assertTrue(actual instanceof StudentStaxEventBuilder);
    }

    @Test(expectedExceptions = StudentXmlException.class)
    public void testCreateStudentBuilderNegativeDomNullArgs() throws StudentXmlException {
        StudentBuilderFactory.createStudentBuilder(null);
    }

    @Test(expectedExceptions = StudentXmlException.class)
    public void testCreateStudentBuilderNegativeDomUnsupportedBuilderType() throws StudentXmlException {
        StudentBuilderFactory.createStudentBuilder(StudentBuilderType.UNSUPPORTED);
    }
}