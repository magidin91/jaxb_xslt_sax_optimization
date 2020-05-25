package org.development;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Serialization in xml
 */
public class StoreXML {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreXML.class);
    private final File target;

    public StoreXML(File target) {
        this.target = target;
    }

    /**
     * Serializes data from list to the target file in XML
     */
    public void save(List<Entry> list) {
        try (FileWriter writer = new FileWriter(target)) {
            JAXBContext context = JAXBContext.newInstance(Entries.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(new Entries(list), writer);
        } catch (IOException | JAXBException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
