package org.development;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionCreatorTest {

    @Test
    public void connectionNotNull() {
        assertNotNull(new ConnectionCreator().init());
    }
}