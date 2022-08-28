package ua.com.andromeda.homework20.config;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/test_jdbc";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1337";
    private static JdbcConfig instance;
    private final Connection connection;

    @SneakyThrows
    private JdbcConfig() {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static JdbcConfig getInstance() {
        if (instance == null) {
            instance = new JdbcConfig();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
