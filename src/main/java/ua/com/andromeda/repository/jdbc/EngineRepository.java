package ua.com.andromeda.repository.jdbc;

import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.JdbcConfig;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.utils.mapper.EngineMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Singleton
public class EngineRepository {
    private final Connection connection;

    public EngineRepository(JdbcConfig jdbcConfig) {
        connection = jdbcConfig.getConnection();
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
            EngineMapper.mapEngineToRow(preparedStatement, engine);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
