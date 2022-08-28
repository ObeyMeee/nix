package ua.com.andromeda.homework14.garage.actions;

import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.service.TruckService;
import ua.com.andromeda.homework10.service.VehicleService;

public class Print implements Command {
    private static final VehicleService<Truck> VEHICLE_SERVICE = TruckService.getInstance();

    @Override
    public void execute() {
        VEHICLE_SERVICE.printAll();
    }
}
