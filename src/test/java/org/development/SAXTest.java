package org.development;

import org.development.transformations.SAX;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SAXTest {
    private final String ln = System.lineSeparator();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void createXMLConvertXSLTPParseSAX() throws IOException {
        String sourceXML = new StringJoiner(ln)
                .add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .add("<entries>")
                .add("    <entry field=\"1\"/>")
                .add("    <entry field=\"2\"/>")
                .add("    <entry field=\"3\"/>")
                .add("</entries>").toString();
        File source = folder.newFile("source.xml");
        try (PrintWriter out = new PrintWriter(source)) {
            out.print(sourceXML);
        }
        assertThat(new SAX().parse(source), is(6));
    }
}