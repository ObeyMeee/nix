package ua.com.andromeda.homework14.garage.actions;

import ua.com.andromeda.homework10.service.VehicleGarageService;

public class Print implements Command {
    private static final VehicleGarageService VEHICLE_GARAGE_SERVICE = VehicleGarageService.getInstance();

    @Override
    public void execute() {
        VEHICLE_GARAGE_SERVICE.print();
    }
}
