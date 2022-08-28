package ua.com.andromeda.homework14.garage.actions;

import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.service.TruckService;
import ua.com.andromeda.homework10.service.VehicleService;
import ua.com.andromeda.homework14.garage.utils.UserInputUtils;

import java.util.Optional;

public class Find implements Command {
    private static final VehicleService<Truck> VEHICLE_SERVICE = TruckService.getInstance();

    @Override
    public void execute() {
        String id = UserInputUtils.getString("Input id of desired vehicle ==> ");
        Optional<Truck> foundedVehicle = VEHICLE_SERVICE.findById(id);
        foundedVehicle.ifPresentOrElse(vehicle -> System.out.println("vehicle = " + vehicle),
                () -> System.out.println("Couldn't find vehicle by id = " + id));
    }
}
