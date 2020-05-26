package org.development;

import org.development.connection.ConnectionCreator;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionCreatorTest {

    @Test
    public void connectionNotNull() {
        assertNotNull(new ConnectionCreator().init());
    }
}