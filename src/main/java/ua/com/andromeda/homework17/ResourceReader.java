package ua.com.andromeda.homework17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceReader {

    private ResourceReader(){

    }

    public static String readFile(String fileName) {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        StringBuilder builder = new StringBuilder();
        try (InputStream stream = classLoader.getResourceAsStream(fileName)) {
            assert stream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line).append(System.lineSeparator());
                    line = reader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
