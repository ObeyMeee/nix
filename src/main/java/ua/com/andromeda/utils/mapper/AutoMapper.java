package ua.com.andromeda.utils.mapper;

import org.bson.Document;
import org.bson.types.BasicBSONList;
import org.bson.types.Decimal128;
import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.Detail;
import ua.com.andromeda.model.Engine;
import ua.com.andromeda.model.Invoice;
import ua.com.andromeda.model.Manufacturer;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.repository.jdbc.EngineRepository;
import ua.com.andromeda.repository.jdbc.VehicleRepository;
import ua.com.andromeda.repository.mongodb.DetailRepositoryMongoDbImpl;
import ua.com.andromeda.repository.mongodb.EngineRepositoryMongoDbImpl;
import ua.com.andromeda.repository.mongodb.InvoiceRepositoryMongoDbImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AutoMapper {

    private static final ApplicationContext CONTEXT = ApplicationContext.getInstance();

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
        EngineRepository engineRepository = CONTEXT.get(EngineRepository.class);
        VehicleRepository vehicleRepository = CONTEXT.get(VehicleRepository.class);
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

    public static Document mapAutoToDocument(Auto vehicle) {
        Document document = new Document();
        document.append("_id", vehicle.getId());
        document.append("model", vehicle.getModel());
        document.append("count", vehicle.getCount());
        document.append("bodyType", vehicle.getBodyType());
        document.append("currency", vehicle.getCurrency());
        document.append("engineId", vehicle.getEngine().getId());
        BasicBSONList basicBSONList = new BasicBSONList();
        List<Detail> details = vehicle.getDetails();
        for (int i = 0; i < details.size(); i++) {
            basicBSONList.put(String.valueOf(i), details.get(i).getId());
        }

        document.append("details", basicBSONList);
        document.append("manufacturer", vehicle.getManufacturer().toString());
        document.append("price", vehicle.getPrice());
        document.append("created", vehicle.getCreated().toString());
        Invoice invoice = vehicle.getInvoice();
        if (invoice != null) {
            document.append("invoice_id", invoice.getId());
        }
        return document;
    }

    public static Auto mapDocumentToAuto(Document document) {
        Decimal128 price = (Decimal128) document.get("price");
        Engine engine = getEngine(document);
        String invoiceId = document.getString("invoiceId");
        InvoiceRepositoryMongoDbImpl invoiceRepository = ApplicationContext.getInstance().get(InvoiceRepositoryMongoDbImpl.class);
        Invoice invoice = invoiceId == null ? null : invoiceRepository.getById(invoiceId);
        List<String> detailsIds = (List<String>) document.get("details");
        List<Detail> details = detailsIds.stream()
                .map(detailId -> CONTEXT.get(DetailRepositoryMongoDbImpl.class).getById(detailId)
                        .orElse(new Detail()))
                .toList();

        return new Auto.Builder()
                .setId(document.getString("_id"))
                .setBodyType(document.getString("bodyType"))
                .setModel(document.getString("model"))
                .setCurrency(document.getString("currency"))
                .setCount(document.getInteger("count"))
                .setManufacturer(Manufacturer.valueOf(document.getString("manufacturer")))
                .setPrice(price.bigDecimalValue())
                .setEngine(engine)
                .setCreated(LocalDateTime.parse(document.getString("created")))
                .setInvoice(invoice)
                .setDetails(details)
                .build();
    }

    private static Engine getEngine(Document document) {
        String engineId = document.getString("engineId");
        EngineRepositoryMongoDbImpl engineRepository = CONTEXT.get(EngineRepositoryMongoDbImpl.class);
        return engineRepository.getById(engineId).orElse(new Engine());
    }
}
