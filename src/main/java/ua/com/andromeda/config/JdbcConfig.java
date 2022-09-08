package ua.com.andromeda.config;

import lombok.SneakyThrows;
import ua.com.andromeda.annotations.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;

@Singleton
public class JdbcConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/test_jdbc";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1337";
    private final Connection connection;

    @SneakyThrows
    public JdbcConfig() {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    public Connection getConnection() {
        return connection;
    }
}
