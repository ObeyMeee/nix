package ua.com.andromeda.homework14.garage.actions;

import lombok.SneakyThrows;
import ua.com.andromeda.homework20.config.JdbcConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDbTables implements Command {
    private final Connection connection;

    public CreateDbTables() {
        connection = JdbcConfig.getInstance().getConnection();
    }

    @Override
    public void execute() {
        String sql = readFile();
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private String readFile() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("create_tables.sql");
        assert inputStream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        StringBuilder stringBuilder = new StringBuilder();
        while (line != null) {
            stringBuilder.append(line).append(System.lineSeparator());
            line = reader.readLine();
        }
        return stringBuilder.toString();
    }
}
