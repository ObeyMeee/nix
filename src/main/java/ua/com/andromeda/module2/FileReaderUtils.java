package ua.com.andromeda.module2;

import org.apache.commons.text.CaseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtils {


    public String[] getFieldNames() {
        BufferedReader reader = getBufferedReader();
        String header;
        try {
            header = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] properties = header.split(",");
        for (int i = 0; i < properties.length; i++) {
            properties[i] = CaseUtils.toCamelCase(properties[i], false, ' ');
        }
        return properties;
    }

    private static BufferedReader getBufferedReader() {
        InputStream inputStream = getInputStream();
        assert inputStream != null;
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private static InputStream getInputStream() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream("products.csv");
    }

    public List<String> getProductsListAsString() {
        BufferedReader reader = getBufferedReader();
        List<String> products = new ArrayList<>();
        String line;
        try {
            // skip header
            reader.readLine();
            line = reader.readLine();
            while (line != null) {
                products.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return products;
    }


}
