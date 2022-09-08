package ua.com.andromeda.actions;

import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.service.AutoService;
import ua.com.andromeda.service.VehicleService;
import ua.com.andromeda.utils.UserInputUtils;

import java.util.Optional;

public class Find implements Command {
    private static final VehicleService<Auto> VEHICLE_SERVICE =
            ApplicationContext.getInstance().get(AutoService.class);

    @Override
    public void execute() {
        String id = UserInputUtils.getString("Input id of desired vehicle ==> ");
        Optional<Auto> foundedVehicle = VEHICLE_SERVICE.findById(id);
        foundedVehicle.ifPresentOrElse(vehicle -> System.out.println("vehicle = " + vehicle),
                () -> System.out.println("Couldn't find vehicle by id = " + id));
    }
}
