package org.development.transformations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

/**
 * SAX parser
 */
public class SAX {
    private static final Logger LOGGER = LoggerFactory.getLogger(SAX.class);
    private int sum;

    /**
     * Parses the file and returns the arithmetic sum of the values of a "field" attribute
     */
    public int parse(File source) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler();
            parser.parse(source, handler);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return sum;
    }

    private class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equals("entry")) {
                int field = Integer.parseInt(attributes.getValue("field"));
                sum += field;
            }
        }
    }
}
