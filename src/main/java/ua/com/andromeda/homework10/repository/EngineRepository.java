package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.mapper.Mapper;
import ua.com.andromeda.homework10.model.Engine;
import ua.com.andromeda.homework20.config.JdbcConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EngineRepository {
    private static EngineRepository instance;
    private final Connection connection;

    private EngineRepository() {
        connection = JdbcConfig.getInstance().getConnection();
    }

    public static EngineRepository getInstance() {
        if (instance == null) {
            instance = new EngineRepository();
        }
        return instance;
    }

    public String getEngineIdByOtherFields(Engine engine) {
        String sql = "SELECT id FROM engines WHERE brand = ? AND volume = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex++, engine.getBrand());
            preparedStatement.setInt(parameterIndex, engine.getVolume());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("id");
            }
            return "";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Engine engine, String engineId) {
        String sql = "UPDATE engines SET brand = ?, volume = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex++, engine.getBrand());
            preparedStatement.setInt(parameterIndex++, engine.getVolume());
            preparedStatement.setString(parameterIndex, engineId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveEngine(Engine engine) {
        String sql = "INSERT INTO engines(id, brand, volume) " +
                "VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            Mapper.mapEngineToRow(preparedStatement, engine);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
