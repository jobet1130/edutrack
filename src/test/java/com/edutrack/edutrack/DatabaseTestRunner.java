package com.edutrack.edutrack;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseTestRunner {

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("DataSource bean should not be null")
    public void testDataSourceIsNotNull() {
        assertNotNull(dataSource, "DataSource should not be null");
    }

    @Test
    @DisplayName("Database connection should succeed")
    public void testDatabaseConnection() throws SQLException {
        // Java 21 allows a more concise try-with-resources syntax
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "Should be able to get a connection from the DataSource");
            System.out.println("Successfully connected to the database: " + connection.getCatalog());
        }
    }
}
