package ua.com.andromeda.homework16;

import ua.com.andromeda.homework10.model.Auto;
import ua.com.andromeda.homework10.model.Manufacturer;
import ua.com.andromeda.homework10.service.AutoService;
import ua.com.andromeda.homework19.ApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final AutoService AUTO_SERVICE = ApplicationContext.getInstance().get(AutoService.class);
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Creating autos...");
        AUTO_SERVICE.createVehicles(10);
        Thread.sleep(300);
        AUTO_SERVICE.printAll();

        String repeatEqualitySign = "=".repeat(20);
        System.out.println(repeatEqualitySign);

        AUTO_SERVICE.printSummaryPrice();
        AUTO_SERVICE.printVehiclePriceStatistics();
        System.out.println(repeatEqualitySign);

        AUTO_SERVICE.sortByModelAndRemoveDuplicatesToMap();

        searchForAutosByPrice();
        checkOnDetailInList();

        AUTO_SERVICE.testAllVehiclesHasPrice();

        Map<String, Object> map = getSimpleMap();
        Auto vehicleFromMap = AUTO_SERVICE.getVehicleFromMap(map);
        System.out.println("vehicleFromMap = " + vehicleFromMap);
    }

    private static void searchForAutosByPrice() throws IOException {
        System.out.println("\nSearch for autos with a price higher than the specified one...\n");
        System.out.print("Input price ==> ");
        BigDecimal price = new BigDecimal(READER.readLine());
        AUTO_SERVICE.printVehicles(price);
    }

    private static void checkOnDetailInList() throws IOException {
        System.out.println("Check if there is specified detail in list of auto...");
        System.out.print("Input detail ==> ");
        String detail = READER.readLine();
        boolean hasDetail = AUTO_SERVICE.hasDetail(detail);
        if (hasDetail) {
            System.out.println(detail + " is founded");
        } else {
            System.out.println("No detail found.");
        }
    }

    private static Map<String, Object> getSimpleMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("model", "ModelFromMap");
        map.put("manufacturer", Manufacturer.BMW);
        map.put("price", BigDecimal.TEN);
        map.put("details", List.of("detailsFromMap1", "detailsFromMap2", "detailsFromMap3"));
        return map;
    }
}
