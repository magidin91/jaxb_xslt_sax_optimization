package org.development;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StoreSQLTest {

    @Test
    public void generate5EntriesAndLoad() {
        List<Entry> exp = List.of(new Entry(1), new Entry(2), new Entry(3),
                new Entry(4), new Entry(5));
        StoreSQL storeSQL = new StoreSQL(new ConnectionCreator().init());
        storeSQL.generate(5);
        assertThat(storeSQL.load(), is(exp));
    }
}