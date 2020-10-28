package com.verbitsky.task4.builder;

import com.verbitsky.task4.exception.StudentXmlException;

public interface XmlStudentBuilder {
    void buildStudentsSet(String xmlPath, String xsdPath) throws StudentXmlException;
}