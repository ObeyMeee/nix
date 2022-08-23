package ua.com.andromeda.homework14.garage.actions;

import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework10.service.VehicleGarageService;
import ua.com.andromeda.homework14.garage.utils.UserInputUtils;

import java.util.Optional;

public class Find implements Command {
    private static final VehicleGarageService VEHICLE_GARAGE_SERVICE =
            context.get(VehicleGarageService.class);

    @Override
    public void execute() {
        int index = UserInputUtils.getInt("Input index of desired vehicle ==> ");
        Optional<Vehicle> foundedVehicle = VEHICLE_GARAGE_SERVICE.findByIndex(index);
        foundedVehicle.ifPresentOrElse(vehicle -> System.out.println("vehicle = " + vehicle),
                () -> System.out.println("Couldn't find vehicle by index = " + index));
    }
}
