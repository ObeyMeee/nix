package ua.com.andromeda.config;

import lombok.SneakyThrows;
import ua.com.andromeda.annotations.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;

@Singleton
public class JdbcConfig {
    private static final String URL = "jdbc:postgresql://ec2-18-208-55-135.compute-1.amazonaws.com/dfn7rh7jm4g1v9";
    private static final String USERNAME = "beuphqsiyszphy";
    private static final String PASSWORD = "b40be8cd73627658411c1e726abaef3a140f0e97bf043463d9d88baff83dbfac";
    private final Connection connection;

    @SneakyThrows
    public JdbcConfig() {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    public Connection getConnection() {
        return connection;
    }
}
