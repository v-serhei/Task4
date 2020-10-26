package com.verbitsky.task4.handler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class StudentErrorHandler extends DefaultHandler {
    private static Logger logger = LogManager.getLogger();
    private static final String DELIMITER = ": ";
    private static final String ROW_PREFIX = "row";
    private static final String COLUMN_PREFIX = " : column";

    @Override
    public void warning(SAXParseException e) throws SAXException {
        logger.log(Level.WARN, getLineAddress(e).concat(DELIMITER).concat(e.getMessage()));
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        logger.log(Level.ERROR, getLineAddress(e).concat(DELIMITER).concat(e.getMessage()));
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        logger.log(Level.FATAL, getLineAddress(e).concat(DELIMITER).concat(e.getMessage()));
    }

    private String getLineAddress (SAXParseException e) {
        StringBuilder sb = new StringBuilder();
        sb.append(ROW_PREFIX);
        sb.append(e.getLineNumber());
        sb.append(COLUMN_PREFIX);
        sb.append(e.getColumnNumber());
        return sb.toString();
    }
}
