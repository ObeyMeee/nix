package ua.com.andromeda.repository.jdbc;

import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.config.JdbcConfig;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.repository.CrudRepository;
import ua.com.andromeda.utils.mapper.AutoMapper;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class AutoRepository implements CrudRepository<Auto> {
    private static final String SEARCH_ALL_AUTOS =
            "SELECT a.id, body_type, vehicle_id, engine_id, price, currency, count, manufacturer, model, created, brand, volume " +
                    "FROM autos a " +
                    "JOIN vehicles v " +
                    "ON a.vehicle_id = v.id " +
                    "JOIN engines e " +
                    "ON a.engine_id = e.id";
    private final VehicleRepository vehicleRepository;
    private final EngineRepository engineRepository;
    private final Connection connection;

    @Autowired
    public AutoRepository(VehicleRepository vehicleRepository, EngineRepository engineRepository) {
        this.vehicleRepository = vehicleRepository;
        this.engineRepository = engineRepository;
        connection = ApplicationContext.getInstance().get(JdbcConfig.class).getConnection();
    }


    @Override
    public Optional<Auto> findById(String id) {
        String sql = SEARCH_ALL_AUTOS + "\nWHERE a.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Auto auto = AutoMapper.mapRowToAuto(resultSet);
                return Optional.of(auto);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Auto> getAll() {
        List<Auto> autos = new LinkedList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SEARCH_ALL_AUTOS)) {

            while (resultSet.next()) {
                Auto auto = AutoMapper.mapRowToAuto(resultSet);
                autos.add(auto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return autos;
    }

    @Override
    public boolean save(Auto auto) {
        if (auto == null) {
            throw new IllegalArgumentException("Auto must not be null");
        }
        if (auto.getPrice().equals(BigDecimal.ZERO)) {
            auto.setPrice(BigDecimal.valueOf(-1));
        }
        String sql = "INSERT INTO autos(id, body_type, count, engine_id, currency, created, vehicle_id) " +
                "VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (!vehicleRepository.saveVehicle(auto) || !engineRepository.saveEngine(auto.getEngine())) {
                return false;
            }
            AutoMapper.mapAutoToRow(preparedStatement, auto);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<Auto> autos) {
        if (autos == null) {
            throw new IllegalArgumentException("auto cannot be null");
        }
        autos.forEach(this::save);
    }

    @Override
    public void update(Auto auto) {
        String sql = "UPDATE autos " +
                "SET body_type=?, count=?, currency=?, created=?" +
                "WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String engineId = getFieldById("engine_id", auto.getId());
            engineRepository.update(auto.getEngine(), engineId);
            String vehicleId = getFieldById("vehicle_id", auto.getId());
            vehicleRepository.updateVehicle(auto, vehicleId);
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex++, auto.getBodyType());
            preparedStatement.setInt(parameterIndex++, auto.getCount());
            preparedStatement.setString(parameterIndex++, auto.getCurrency());
            preparedStatement.setTimestamp(parameterIndex++, Timestamp.valueOf(auto.getCreated()));
            preparedStatement.setString(parameterIndex, auto.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFieldById(String field, String autoId) {
        String sql = "SELECT " + field + " FROM autos WHERE autos.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, autoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(field);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM autos WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAutoIdByOtherFields(Auto auto) {
        String sql = "SELECT id " +
                "FROM autos " +
                "WHERE body_type = ? AND" +
                " count = ? AND" +
                " engine_id = ? AND" +
                " currency = ? AND" +
                " created = ? AND" +
                " vehicle_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            mapAutoToRow(auto, preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    private void mapAutoToRow(Auto auto, PreparedStatement preparedStatement) throws SQLException {
        int parameterIndex = 1;
        preparedStatement.setString(parameterIndex++, auto.getBodyType());
        preparedStatement.setInt(parameterIndex++, auto.getCount());
        preparedStatement.setString(parameterIndex++, engineRepository.getEngineIdByOtherFields(auto.getEngine()));
        preparedStatement.setString(parameterIndex++, auto.getCurrency());
        preparedStatement.setTimestamp(parameterIndex++, Timestamp.valueOf(auto.getCreated()));
        preparedStatement.setString(parameterIndex, vehicleRepository.getVehicleIdByOtherFields(auto));
    }
}
