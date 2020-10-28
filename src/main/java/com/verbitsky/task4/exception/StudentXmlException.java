package com.verbitsky.task4.exception;

public class StudentXmlException extends Exception {
    public StudentXmlException() {
        super();
    }

    public StudentXmlException(String message) {
        super(message);
    }

    public StudentXmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentXmlException(Throwable cause) {
        super(cause);
    }
}
