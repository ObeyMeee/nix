package ua.com.andromeda.utils.mapper;

import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.model.cars.Truck;
import ua.com.andromeda.repository.jdbc.AutoRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TruckMapper {
    private TruckMapper() {

    }

    public static void mapTruckToRow(PreparedStatement preparedStatement, Truck truck) throws SQLException {
        int parameterIndex = 1;
        AutoRepository autoRepository = ApplicationContext.getInstance().get(AutoRepository.class);
        String autoId = autoRepository.getAutoIdByOtherFields(truck);
        preparedStatement.setString(parameterIndex++, truck.getId());
        preparedStatement.setInt(parameterIndex++, truck.getMaxCarryingCapacity());
        preparedStatement.setString(parameterIndex, autoId);
    }

    public static Truck mapRowToTruck(ResultSet resultSet) throws SQLException {
        Auto auto = AutoMapper.mapRowToAuto(resultSet);
        Truck truck = new Truck(auto);
        truck.setMaxCarryingCapacity(resultSet.getInt("carrying_capacity"));
        return truck;
    }
}
