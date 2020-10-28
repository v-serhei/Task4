package com.verbitsky.task4.factory;

import com.verbitsky.task4.builder.AbstractStudentBuilder;
import com.verbitsky.task4.builder.impl.StudentDomBuilder;
import com.verbitsky.task4.builder.impl.StudentSaxBuilder;
import com.verbitsky.task4.builder.impl.StudentStaxEventBuilder;
import com.verbitsky.task4.builder.impl.StudentStaxStreamBuilder;
import com.verbitsky.task4.exception.StudentXmlException;

public class StudentBuilderFactory {

    private StudentBuilderFactory() {
    }

    public static AbstractStudentBuilder createStudentBuilder(StudentBuilderType type) throws StudentXmlException {
        if (type != null) {
            switch (type) {
                case DOM: {
                    return new StudentDomBuilder();
                }
                case SAX: {
                    return new StudentSaxBuilder();
                }
                case STAX_STREAM: {
                    return new StudentStaxStreamBuilder();
                }
                case STAX_EVENT: {
                    return new StudentStaxEventBuilder();
                }
                default: {
                    throw new StudentXmlException("Received unsupported StudentBuilder type: " + type.name());
                }
            }
        } else {
            throw new StudentXmlException("Received null StudentBuilder type");
        }
    }
}