package ua.com.andromeda.homework14.garage.utils;

import lombok.SneakyThrows;
import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework10.model.VehicleType;
import ua.com.andromeda.homework10.service.AutoService;
import ua.com.andromeda.homework10.service.SportCarService;
import ua.com.andromeda.homework10.service.TruckService;
import ua.com.andromeda.homework19.ApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class UserInputUtils {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));


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
        ApplicationContext context = ApplicationContext.getInstance();
        AutoService autoService = context.get(AutoService.class);
        SportCarService sportCarService = context.get(SportCarService.class);
        TruckService truckService = context.get(TruckService.class);
        return switch (vehicleTypes[userInput]) {
            case AUTO -> autoService.createRandomVehicle();
            case SPORT_CAR -> sportCarService.createRandomVehicle();
            case TRUCK -> truckService.createRandomVehicle();
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
