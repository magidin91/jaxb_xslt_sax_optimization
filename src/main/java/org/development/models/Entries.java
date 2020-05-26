package org.development.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "entries")
@XmlRootElement
public class Entries {
    @XmlElement(name = "entry")
    private List<Entry> list;

    public Entries() {
    }

    public Entries(List<Entry> list) {
        this.list = list;
    }
}
