package ua.com.andromeda.repository.jdbc;

import ua.com.andromeda.annotations.Autowired;
import ua.com.andromeda.annotations.Singleton;
import ua.com.andromeda.config.JdbcConfig;
import ua.com.andromeda.model.Invoice;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Singleton
public class InvoiceRepositoryJdbcImpl {
    private final Connection connection;

    @Autowired
    public InvoiceRepositoryJdbcImpl(JdbcConfig jdbcConfig) {
        connection = jdbcConfig.getConnection();
    }


    public boolean save(Invoice invoice) {
        String sql = "INSERT INTO invoices(id, created_at) VALUES(?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            preparedStatement.setString(parameterIndex++, invoice.getId());
            preparedStatement.setTimestamp(parameterIndex, Timestamp.valueOf(invoice.getCreated()));
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Invoice> getInvoicesWhereTotalPriceGreaterThan(BigDecimal totalPrice) {
        String sql = "SELECT *\n" +
                "FROM (SELECT i.id, i.created_at, SUM(v.price * a.count) AS total_price\n" +
                "      FROM invoices i\n" +
                "               JOIN vehicles v on i.id = v.invoice_id\n" +
                "               JOIN autos a ON a.vehicle_id = v.id\n" +
                "      GROUP BY i.id) AS grouped_invoices\n" +
                "WHERE total_price > ?\n";
        LinkedList<Invoice> invoices = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBigDecimal(1, totalPrice);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(resultSet.getString("id"));
                invoice.setCreated(resultSet.getTimestamp("created_at").toLocalDateTime());
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return invoices;
    }

    public int getInvoicesCount() {
        String sql = "SELECT COUNT(*) as count FROM invoices";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void updateTime(String id) {
        String sql = "SELECT * FROM invoices;\n" +
                "UPDATE invoices SET created_at = now()\n" +
                "WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Map<String, BigDecimal> groupByTotalPrice() {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        String sql = "SELECT i.id, SUM(v.price * a.count) AS total_price\n" +
                "FROM invoices i\n" +
                "JOIN vehicles v on i.id = v.invoice_id\n" +
                "JOIN autos a ON a.vehicle_id = v.id\n" +
                "GROUP BY i.id\n" +
                "ORDER BY total_price";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                map.put(resultSet.getString("id"), resultSet.getBigDecimal("total_price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
