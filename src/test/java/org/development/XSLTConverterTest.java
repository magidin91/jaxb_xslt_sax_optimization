package org.development;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class XSLTConverterTest {
    private final String ln = System.lineSeparator();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void convertXMLAndRead() throws IOException, URISyntaxException {
        File source = folder.newFile("source.xml");
        File dest = folder.newFile("dest.xml");
        URL resource = getClass().getClassLoader().getResource("schema.xsl");
        File schema = Paths.get(resource.toURI()).toFile();
        String sourceXML = new StringJoiner(ln).add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                .add("<entries>")
                .add("    <entry>")
                .add("        <field>1</field>")
                .add("    </entry>")
                .add("    <entry>")
                .add("        <field>2</field>")
                .add("    </entry>")
                .add("    <entry>")
                .add("        <field>3</field>")
                .add("    </entry>")
                .add("</entries>").toString();
        try (PrintWriter out = new PrintWriter(source)) {
            out.print(sourceXML);
        }
        new XSLTConverter().convert(source, dest, schema);
        StringJoiner rslXML = new StringJoiner(ln);
        try (BufferedReader in = new BufferedReader(new FileReader(dest))) {
            in.lines().forEach(rslXML::add);
        }
        String expectedXML = new StringJoiner(ln)
                .add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .add("<entries>")
                .add("    <entry field=\"1\"/>")
                .add("    <entry field=\"2\"/>")
                .add("    <entry field=\"3\"/>")
                .add("</entries>").toString();
        assertThat(rslXML.toString(), is(expectedXML));
    }
}