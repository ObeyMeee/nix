package ua.com.andromeda.repository.jdbc;

import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.JdbcConfig;
import ua.com.andromeda.model.cars.SportCar;
import ua.com.andromeda.repository.CrudRepository;
import ua.com.andromeda.utils.mapper.SportCarMapper;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Singleton
public class SportCarRepository implements CrudRepository<SportCar> {
    private static final String SEARCH_ALL_SPORT_CARS = "SELECT s.id, body_type, vehicle_id, engine_id, price, currency, count, manufacturer, model, created, brand, volume, max_speed " +
            "FROM sport_cars s " +
            "JOIN autos a ON s.auto_id = a.id " +
            "JOIN vehicles v " +
            "ON a.vehicle_id = v.id " +
            "JOIN engines e " +
            "ON a.engine_id = e.id";
    private final AutoRepository autoRepository;
    private final Connection connection;

    @Autowired
    public SportCarRepository(JdbcConfig jdbcConfig, AutoRepository autoRepository) {
        connection = jdbcConfig.getConnection();
        this.autoRepository = autoRepository;
    }

    @Override
    public Optional<SportCar> findById(String id) {
        String searchSportCarById = SEARCH_ALL_SPORT_CARS + " WHERE s.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(searchSportCarById)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                SportCar sportCar = SportCarMapper.mapRowToSportCar(resultSet);
                return Optional.of(sportCar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<SportCar> getAll() {
        List<SportCar> sportCars = new LinkedList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SEARCH_ALL_SPORT_CARS)) {

            while (resultSet.next()) {
                SportCar sportCar = SportCarMapper.mapRowToSportCar(resultSet);
                sportCars.add(sportCar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sportCars;
    }

    @Override
    public boolean save(SportCar sportCar) {
        if (sportCar == null) {
            throw new IllegalArgumentException("sport car cannot be null");
        }
        if (sportCar.getPrice().equals(BigDecimal.ZERO)) {
            sportCar.setPrice(BigDecimal.valueOf(-1));
        }

        String sql = "INSERT INTO sport_cars(id, max_speed, auto_id) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (!saveAuto(sportCar)) {
                return false;
            }
            SportCarMapper.mapSportCarToRow(preparedStatement, sportCar);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean saveAuto(SportCar sportCar) {
        return autoRepository.save(sportCar);
    }

    @Override
    public void saveAll(List<SportCar> sportCarsToAdd) {
        if (sportCarsToAdd == null) {
            throw new IllegalArgumentException("sport car cannot be null");
        }
        sportCarsToAdd.forEach(this::save);
    }

    @Override
    public void update(SportCar sportCar) {
        String sql = "UPDATE sport_cars SET max_speed = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            preparedStatement.setInt(parameterIndex++, sportCar.getMaxSpeed());
            preparedStatement.setString(parameterIndex, sportCar.getId());
            String autoId = autoRepository.getFieldById("id", sportCar.getId());
            autoRepository.update(sportCar);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM sport_cars WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
