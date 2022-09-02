package ua.com.andromeda.utils.mapper;

import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.model.Manufacturer;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.repository.jdbc.EngineRepository;
import ua.com.andromeda.repository.jdbc.VehicleRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class AutoMapper {
    private AutoMapper() {

    }

    public static Auto mapRowToAuto(ResultSet resultSet) throws SQLException {
        Engine engine = EngineMapper.mapRowToEngine(resultSet);
        String manufacturer = resultSet.getString("manufacturer").toUpperCase();
        Manufacturer manufacturer1 = Manufacturer.valueOf(manufacturer);
        return new Auto.Builder()
                .setId(resultSet.getString("id"))
                .setModel(resultSet.getString("model"))
                .setManufacturer(manufacturer1)
                .setPrice(resultSet.getBigDecimal("price"))
                .setCount(resultSet.getInt("count"))
                .setBodyType(resultSet.getString("body_type"))
                .setCurrency(resultSet.getString("currency"))
                .setCreated(resultSet.getTimestamp("created").toLocalDateTime())
                .setEngine(engine)
                .build();
    }

    public static void mapAutoToRow(PreparedStatement preparedStatement, Auto auto) throws SQLException {
        EngineRepository engineRepository = ApplicationContext.getInstance().get(EngineRepository.class);
        VehicleRepository vehicleRepository = ApplicationContext.getInstance().get(VehicleRepository.class);
        int parameterIndex = 1;
        String id = UUID.randomUUID().toString();
        preparedStatement.setString(parameterIndex++, id);
        preparedStatement.setString(parameterIndex++, auto.getBodyType());
        preparedStatement.setInt(parameterIndex++, auto.getCount());
        String engineId = engineRepository.getEngineIdByOtherFields(auto.getEngine());
        preparedStatement.setString(parameterIndex++, engineId);
        preparedStatement.setString(parameterIndex++, auto.getCurrency());
        preparedStatement.setTimestamp(parameterIndex++, Timestamp.valueOf(auto.getCreated()));
        String vehicleId = vehicleRepository.getVehicleIdByOtherFields(auto);
        preparedStatement.setString(parameterIndex, vehicleId);
    }

}
