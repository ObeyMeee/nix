package ua.com.andromeda.repository.jdbc;

import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.JdbcConfig;
import ua.com.andromeda.model.cars.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Singleton
public class VehicleRepository {
    private final Connection connection;

    @Autowired
    public VehicleRepository(JdbcConfig jdbcConfig) {
        connection = jdbcConfig.getConnection();
    }

    public String getVehicleIdByOtherFields(Vehicle vehicle) {
        String sql = "SELECT id FROM vehicles WHERE model = ? AND price = ? AND manufacturer = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex++, vehicle.getModel());
            preparedStatement.setBigDecimal(parameterIndex++, vehicle.getPrice());
            preparedStatement.setString(parameterIndex, vehicle.getManufacturer().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("id");
            }
            return "";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateVehicle(Vehicle vehicle, String id) {
        String sql = "UPDATE vehicles " +
                "SET model=?, price=?, manufacturer=? " +
                "WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex++, vehicle.getModel());
            preparedStatement.setBigDecimal(parameterIndex++, vehicle.getPrice());
            preparedStatement.setString(parameterIndex++, vehicle.getManufacturer().toString());
            preparedStatement.setString(parameterIndex, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles(id, model, price, manufacturer, invoice_id) " +
                "VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            String id = UUID.randomUUID().toString();
            preparedStatement.setString(parameterIndex++, id);
            preparedStatement.setString(parameterIndex++, vehicle.getModel());
            preparedStatement.setBigDecimal(parameterIndex++, vehicle.getPrice());
            preparedStatement.setString(parameterIndex++, vehicle.getManufacturer().toString());
            preparedStatement.setString(parameterIndex, vehicle.getInvoice().getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
