package ua.com.andromeda.utils.mapper;

import ua.com.andromeda.model.Engine;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EngineMapper {


    private EngineMapper() {

    }

    public static Engine mapRowToEngine(ResultSet resultSet) throws SQLException {
        Engine engine = new Engine();
        engine.setId(resultSet.getString("engine_id"));
        engine.setBrand(resultSet.getString("brand"));
        engine.setVolume(resultSet.getInt("volume"));
        return engine;
    }


    public static void mapEngineToRow(PreparedStatement preparedStatement, Engine engine) throws SQLException {
        int parameterIndex = 1;
        preparedStatement.setString(parameterIndex++, engine.getId());
        preparedStatement.setString(parameterIndex++, engine.getBrand());
        preparedStatement.setInt(parameterIndex, engine.getVolume());
    }

}
