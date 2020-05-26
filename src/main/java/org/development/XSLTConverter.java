package org.development;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * XSLT transformation
 */
public class XSLTConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(XSLTConverter.class);
    /**
     * Сonverts an XML file using an XSL template to a file in a different XML format
     */
    public void convert(File source, File dest, File scheme) {
        try (FileInputStream inScheme = new FileInputStream(scheme);
             FileInputStream inSource = new FileInputStream(source);
             FileOutputStream out = new FileOutputStream(dest)) {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(inScheme));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            transformer.transform(new StreamSource(inSource), new StreamResult(out));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

