package ua.com.andromeda.homework14.garage.utils;

import lombok.SneakyThrows;
import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework10.model.VehicleType;
import ua.com.andromeda.homework10.service.AutoService;
import ua.com.andromeda.homework10.service.SportCarService;
import ua.com.andromeda.homework10.service.TruckService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class UserInputUtils {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private static final AutoService AUTO_SERVICE = AutoService.getInstance();
    private static final SportCarService SPORT_CAR_SERVICE = SportCarService.getInstance();
    private static final TruckService TRUCK_SERVICE = TruckService.getInstance();

    private UserInputUtils() {

    }

    @SneakyThrows
    public static int getInt(String outputClue) {
        System.out.print(outputClue);
        return Integer.parseInt(READER.readLine());
    }

    @SneakyThrows
    public static String getString(String outputClue) {
        System.out.print(outputClue);
        return READER.readLine();
    }

    public static Vehicle getVehicle() {
        VehicleType[] vehicleTypes = VehicleType.values();

        List<String> vehicleTypesAsStrings = Arrays.stream(vehicleTypes)
                .map(Enum::toString)
                .toList();

        int userInput = selectGivenActions(vehicleTypes.length, vehicleTypesAsStrings);
        return switch (vehicleTypes[userInput]) {
            case AUTO -> AUTO_SERVICE.createRandomVehicle();
            case SPORT_CAR -> SPORT_CAR_SERVICE.createRandomVehicle();
            case TRUCK -> TRUCK_SERVICE.createRandomVehicle();
        };
    }

    public static int selectGivenActions(int amountOfActions, List<String> actionsList) {
        int userInput;
        do {
            System.out.println("Select option:\n");
            for (int i = 0; i < amountOfActions; i++) {
                System.out.printf("%d. %s%n", i, actionsList.get(i));
            }
            userInput = getInt("Your input ==> ");
        } while (userInput < 0 || userInput >= amountOfActions);
        return userInput;
    }

}
