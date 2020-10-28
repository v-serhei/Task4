package com.verbitsky.task4.builder;

import com.verbitsky.task4.entity.Student;
import com.verbitsky.task4.exception.StudentXmlException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractStudentBuilder implements XmlStudentBuilder {
    protected Set<Student> students;

    public AbstractStudentBuilder() {
        this.students = new HashSet<>();
    }

    public Set<Student> getStudents() {
        return students;
    }

    public abstract void buildStudentsSet(String xmlPath, String xsdPath) throws StudentXmlException;
}
