package ua.com.andromeda.homework10.repository;

import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework20.config.JdbcConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class VehicleRepository {
    private static VehicleRepository instance;

    private final Connection connection;

    private VehicleRepository() {
        connection = JdbcConfig.getInstance().getConnection();
    }

    public static VehicleRepository getInstance() {
        if (instance == null) {
            instance = new VehicleRepository();
        }
        return instance;
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
            preparedStatement.setString(parameterIndex, vehicle.getInvoiceId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
