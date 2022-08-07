package ua.com.andromeda.homework17;

import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Engine;
import ua.com.andromeda.homework10.model.Manufacturer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
    private static final String JSON_FILE = "auto.json";
    private static final  String XML_FILE = "auto.xml";

    private static final String JSON_REGEX =
            "\"(\\w+)\":\\s\"(.+)\"|" +
                    "\"(\\w+)\":\\s\\{(\\n\\s*\"(\\w+)\":\\s\"(.+)\",*)+\\n\\s+}";

    private static final String XML_REGEX = "<([\\w_.-]+)>(.+)<\\/[\\w_.-]+>|" +
            "<([\\w_.-]+)\\s(\\w+)=\"(.+)\">(.+)<\\/[\\w_.-]+>";

    private static final Function<Stream<MatchResult>, Map<String, String>> FILL_MAP_FROM_XML = matchResultStream -> {
        HashMap<String, String> map = new HashMap<>();
        matchResultStream.forEach(matchResult -> {
            map.put(matchResult.group(1), matchResult.group(2));
            map.put(matchResult.group(4), matchResult.group(5));
            map.put(matchResult.group(3), matchResult.group(6));
        });
        return map;
    };

    private static final Function<Stream<MatchResult>, Map<String, String>> FILL_MAP_FROM_JSON = matchResultStream -> {
        HashMap<String, String> map = new HashMap<>();
        matchResultStream.forEach(matchResult -> {
            map.put(matchResult.group(1), matchResult.group(2));
            map.put(matchResult.group(4), matchResult.group(5));
        });
        return map;
    };

    public static void main(String[] args) {

        Auto autoFromXml = parseAutoFromFile(XML_FILE, XML_REGEX, FILL_MAP_FROM_XML);
        Auto autoFromJson = parseAutoFromFile(JSON_FILE, JSON_REGEX, FILL_MAP_FROM_JSON);
        System.out.println("autoFromXml = " + autoFromXml);
        System.out.println("\nautoFromJson = " + autoFromJson);
    }

    private static Auto parseAutoFromFile(String fileName, String regex, Function<Stream<MatchResult>, Map<String, String>> function) {
        String fileContent = ResourceReader.readFile(fileName);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContent);
        return createAuto(function.apply(matcher.results()));
    }

    private static Auto createAuto(Map<String, String> map) {
        Auto auto = new Auto();

        auto.setModel(map.get("model"));
        auto.setManufacturer(Manufacturer.valueOf(map.get("manufacturer")));
        auto.setCount(Integer.parseInt(map.get("count")));
        auto.setBodyType(map.get("bodyType"));
        auto.setCurrency(map.get("currency"));
        auto.setPrice(new BigDecimal(map.get("price")));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime created = LocalDateTime.parse(map.get("created"), formatter);
        auto.setCreated(created);

        Engine engine = new Engine();
        engine.setBrand(map.get("brand"));
        engine.setVolume(Integer.parseInt(map.get("volume")));
        auto.setEngine(engine);

        return auto;
    }
}
