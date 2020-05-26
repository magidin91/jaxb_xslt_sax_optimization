package org.development;

import org.development.connection.ConnectionCreator;
import org.development.models.Entry;
import org.development.transformations.SAX;
import org.development.transformations.StoreSQL;
import org.development.transformations.StoreXML;
import org.development.transformations.XSLTConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * It's an example of using XML_XSLT_SAX optimization:
 * 1) Using JDBC adds 10 entries to the database table and then get a list of entries from the database.
 * 2) Using JAXB serializes entries from the list to the target file in XML format.
 * 3) Using XSLT converts XML file(targetXML) using XSL template to a file(newXML) in a different XML format.
 * 4) Using SAX parses the file and output the arithmetic sum of the values of a "field" attribute.
 */
public class OptimizationExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptimizationExample.class);

    public static void main(String[] args) {
        File targetXML = new File("src/main/resources/targetXML.xml");
        File newXML = new File("src/main/resources/newXML.xml");
        File schema = new File("src/main/resources/schema.xsl");
        List<Entry> entries = getEntries(10);
        new StoreXML(targetXML).save(entries);
        new XSLTConverter().convert(targetXML, newXML, schema);
        System.out.println(new SAX().parse(newXML));
    }

    private static List<Entry> getEntries(int size) {
        List<Entry> entries = null;
        try (StoreSQL storeSQL = new StoreSQL(new ConnectionCreator().init())) {
            storeSQL.generate(size);
            entries = storeSQL.load();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return entries;
    }
}