package ua.com.andromeda.homework10.mapper;

import ua.com.andromeda.homework10.model.*;
import ua.com.andromeda.homework10.repository.AutoRepository;
import ua.com.andromeda.homework10.repository.EngineRepository;
import ua.com.andromeda.homework10.repository.VehicleRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class Mapper {

    private Mapper() {

    }

    public static Auto mapRowToAuto(ResultSet resultSet) throws SQLException {
        Engine engine = mapRowToEngine(resultSet);
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

    public static Engine mapRowToEngine(ResultSet resultSet) throws SQLException {
        Engine engine = new Engine();
        engine.setId(resultSet.getString("engine_id"));
        engine.setBrand(resultSet.getString("brand"));
        engine.setVolume(resultSet.getInt("volume"));
        return engine;
    }

    public static void mapAutoToRow(PreparedStatement preparedStatement, Auto auto) throws SQLException {
        EngineRepository engineRepository = EngineRepository.getInstance();
        VehicleRepository vehicleRepository = VehicleRepository.getInstance();
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

    public static void mapEngineToRow(PreparedStatement preparedStatement, Engine engine) throws SQLException {
        int parameterIndex = 1;
        preparedStatement.setString(parameterIndex++, engine.getId());
        preparedStatement.setString(parameterIndex++, engine.getBrand());
        preparedStatement.setInt(parameterIndex, engine.getVolume());
    }

    public static SportCar mapRowToSportCar(ResultSet resultSet) throws SQLException {
        Auto auto = mapRowToAuto(resultSet);
        SportCar sportCar = new SportCar(auto);
        sportCar.setMaxSpeed(resultSet.getInt("max_speed"));
        return sportCar;
    }

    public static void mapSportCarToRow(PreparedStatement preparedStatement, SportCar sportCar) throws SQLException {
        int parameterIndex = 1;
        String autoId = AutoRepository.getInstance().getAutoIdByOtherFields(sportCar);
        preparedStatement.setString(parameterIndex++, sportCar.getId());
        preparedStatement.setInt(parameterIndex++, sportCar.getMaxSpeed());
        preparedStatement.setString(parameterIndex, autoId);
    }

    public static void mapTruckToRow(PreparedStatement preparedStatement, Truck truck) throws SQLException {
        int parameterIndex = 1;
        String autoId = AutoRepository.getInstance().getAutoIdByOtherFields(truck);
        preparedStatement.setString(parameterIndex++, truck.getId());
        preparedStatement.setInt(parameterIndex++, truck.getMaxCarryingCapacity());
        preparedStatement.setString(parameterIndex, autoId);
    }

    public static Truck mapRowToTruck(ResultSet resultSet) throws SQLException {
        Auto auto = mapRowToAuto(resultSet);
        Truck truck = new Truck(auto);
        truck.setMaxCarryingCapacity(resultSet.getInt("carrying_capacity"));
        return truck;
    }
}
