package org.development;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StoreXMLTest {
    private final String ln = System.lineSeparator();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void saveListAndRead() throws IOException {
        List<Entry> entries = List.of(new Entry(1), new Entry(2), new Entry(3));
        String expected = new StringJoiner(ln).add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
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
        File target = folder.newFile("xmlResult.xml");
        new StoreXML(target).save(entries);
        StringJoiner rsl = new StringJoiner(ln);
        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            in.lines().forEach(rsl::add);
        }
        assertThat(rsl.toString(), is(expected));
    }
}