package ua.com.andromeda.utils.mapper;

import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.model.cars.SportCar;
import ua.com.andromeda.repository.jdbc.AutoRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SportCarMapper {

    private SportCarMapper() {

    }

    public static SportCar mapRowToSportCar(ResultSet resultSet) throws SQLException {
        Auto auto = AutoMapper.mapRowToAuto(resultSet);
        SportCar sportCar = new SportCar(auto);
        sportCar.setMaxSpeed(resultSet.getInt("max_speed"));
        return sportCar;
    }

    public static void mapSportCarToRow(PreparedStatement preparedStatement, SportCar sportCar) throws SQLException {
        AutoRepository autoRepository = ApplicationContext.getInstance().get(AutoRepository.class);
        String autoId = autoRepository.getAutoIdByOtherFields(sportCar);
        int parameterIndex = 1;
        preparedStatement.setString(parameterIndex++, sportCar.getId());
        preparedStatement.setInt(parameterIndex++, sportCar.getMaxSpeed());
        preparedStatement.setString(parameterIndex, autoId);
    }

}
