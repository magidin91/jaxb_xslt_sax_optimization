package org.development;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StoreXMLTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void saveListAndRead() throws IOException {
        List<Entry> entries = List.of(new Entry(1), new Entry(2), new Entry(3));
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<entries>"
                + "    <entry>"
                + "        <field>1</field>"
                + "    </entry>"
                + "    <entry>"
                + "        <field>2</field>"
                + "    </entry>"
                + "    <entry>"
                + "        <field>3</field>"
                + "    </entry>"
                + "</entries>";
        File target = folder.newFile("xmlResult.xml");
        new StoreXML(target).save(entries);
        StringBuilder rsl = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new FileReader(target))) {
            in.lines().forEach(rsl::append);
        }
        assertThat(rsl.toString(), is(expected));
    }

}