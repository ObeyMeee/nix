package ua.com.andromeda.utils.mapper;

import com.google.gson.*;
import org.bson.Document;
import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.Detail;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.model.cars.SportCar;
import ua.com.andromeda.repository.jdbc.AutoRepository;
import ua.com.andromeda.repository.mongodb.DetailRepositoryMongoDbImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SportCarMapper {

    private static final Gson GSON;

    static {
        JsonSerializer<LocalDateTime> serializer = (localDateTime, typeOfSrc, context) ->
                new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        JsonDeserializer<LocalDateTime> deserializer = (json, typeOfT, context) ->
                LocalDateTime.parse(json.getAsString());

        GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, serializer)
                .registerTypeAdapter(LocalDateTime.class, deserializer)
                .create();
    }

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

    public static SportCar mapDocumentToSportCar(Document doc) {
        String json = doc.toJson();
        List<Detail> details = getDetails(doc);
        SportCar sportCar = GSON.fromJson(json, SportCar.class);
        sportCar.setDetails(details);
        return sportCar;
    }

    private static List<Detail> getDetails(Document doc) {
        List<String> detailsIds = doc.get("details", ArrayList.class);
        DetailRepositoryMongoDbImpl repository = ApplicationContext.getInstance().get(DetailRepositoryMongoDbImpl.class);
        return detailsIds.stream()
                .map(repository::getById)
                .map(Optional::get)
                .toList();
    }

    public static Document mapSportCarToDocument(SportCar vehicle) {
        List<String> detailsIds = saveDetailsAndGetIds(vehicle.getDetails());
        Document document = Document.parse(GSON.toJson(vehicle));
        document.append("details", detailsIds);
        return document;
    }

    private static List<String> saveDetailsAndGetIds(List<Detail> details) {
        DetailRepositoryMongoDbImpl repository = ApplicationContext.getInstance().get(DetailRepositoryMongoDbImpl.class);
        List<String> detailsIds = details.stream().map(Detail::getId).toList();
        repository.save(details);
        return detailsIds;
    }
}
