package org.development;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class is responsible for interacting with the database
 */
public class StoreSQL implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreSQL.class);
    private final Connection connection;

    public StoreSQL(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds N entries to the table.
     * Before doing this it creates a table if it is not created and deletes entries if they were in the table.
     */
    public void generate(int size) {
        try {
            connection.setAutoCommit(false);
            prepareTable();
            addEntries(size);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.trace("SQLException. Executing rollback");
            LOGGER.error(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Creates a table if it hasn't been created yet, and deletes all entries
     */
    private void prepareTable() throws SQLException {
        try (Statement st1 = connection.createStatement()) {
            st1.addBatch("CREATE TABLE IF NOT EXISTS entry (field integer)");
            st1.addBatch("Delete from entry");
            st1.executeBatch();
        }
    }

    /**
     * Adds entries to the table
     */
    private void addEntries(int size) throws SQLException {
        try (PreparedStatement st2 = connection.prepareStatement("insert into entry values (?)")) {
            for (int i = 1; i <= size; i++) {
                st2.setInt(1, i);
                st2.addBatch();
            }
            st2.executeBatch();
        }
    }

    /**
     * Returns a list of entries from the database
     */
    public List<Entry> load() {
        List<Entry> entries = new ArrayList<>();
        Entry element;
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery("select* from entry")) {
            while (rs.next()) {
                element = new Entry(rs.getInt("field"));
                entries.add(element);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return entries;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}