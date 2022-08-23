package ua.com.andromeda.homework14.garage.actions;

import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework10.service.VehicleGarageService;
import ua.com.andromeda.homework14.garage.utils.UserInputUtils;


public class Update implements Command {
    private static final VehicleGarageService VEHICLE_GARAGE_SERVICE = context.get(VehicleGarageService.class);

    @Override
    public void execute() {
        int index = UserInputUtils.getInt("Input index to update vehicle ==> ");
        Vehicle vehicle = UserInputUtils.getVehicle();
        VEHICLE_GARAGE_SERVICE.set(index, vehicle);
    }
}
