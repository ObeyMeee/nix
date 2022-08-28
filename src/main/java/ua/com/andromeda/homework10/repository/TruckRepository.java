package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.mapper.Mapper;
import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework20.config.JdbcConfig;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TruckRepository implements CrudRepository<Truck> {
    private static final String SEARCH_ALL_TRUCKS = "SELECT t.id, body_type, vehicle_id, engine_id, price, currency, count, manufacturer, model, created, brand, volume, carrying_capacity " +
            "FROM trucks t " +
            "JOIN autos a ON t.auto_id = a.id " +
            "JOIN vehicles v " +
            "ON a.vehicle_id = v.id " +
            "JOIN engines e " +
            "ON a.engine_id = e.id";
    private static TruckRepository instance;
    private final Connection connection;


    private TruckRepository() {
        connection = JdbcConfig.getInstance().getConnection();
    }

    public static TruckRepository getInstance() {
        if (instance == null) {
            instance = new TruckRepository();
        }
        return instance;
    }

    @Override
    public Optional<Truck> findById(String id) {
        String searchTruckById = SEARCH_ALL_TRUCKS + " WHERE t.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(searchTruckById)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Truck truck = Mapper.mapRowToTruck(resultSet);
                return Optional.of(truck);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Truck> getAll() {
        List<Truck> trucks = new LinkedList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SEARCH_ALL_TRUCKS)) {

            while (resultSet.next()) {
                Truck truck = Mapper.mapRowToTruck(resultSet);
                trucks.add(truck);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trucks;
    }

    @Override
    public boolean save(Truck truck) {
        if (truck == null) {
            throw new IllegalArgumentException("truck cannot be null");
        }
        if (truck.getPrice().equals(BigDecimal.ZERO)) {
            truck.setPrice(BigDecimal.valueOf(-1));
        }

        String sql = "INSERT INTO trucks(id, carrying_capacity, auto_id) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (!saveAuto(truck)) {
                return false;
            }
            Mapper.mapTruckToRow(preparedStatement, truck);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean saveAuto(Truck sportCar) {
        return AutoRepository.getInstance().save(sportCar);
    }

    @Override
    public void saveAll(List<Truck> trucks) {
        if (trucks == null) {
            throw new IllegalArgumentException("trucks cannot be null");
        }
        trucks.forEach(this::save);
    }

    @Override
    public void update(Truck truck, String id) {
        String sql = "UPDATE trucks SET carrying_capacity = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            preparedStatement.setInt(parameterIndex++, truck.getMaxCarryingCapacity());
            preparedStatement.setString(parameterIndex, id);
            AutoRepository autoRepository = AutoRepository.getInstance();
            String autoId = autoRepository.getFieldById("id", id);
            autoRepository.update(truck, autoId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM trucks WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
