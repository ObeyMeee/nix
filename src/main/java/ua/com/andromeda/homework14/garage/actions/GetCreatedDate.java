package ua.com.andromeda.homework14.garage.actions;

import ua.com.andromeda.homework10.service.VehicleGarageService;
import ua.com.andromeda.homework14.garage.utils.UserInputUtils;


public class GetCreatedDate implements Command {
    private static final VehicleGarageService VEHICLE_GARAGE_SERVICE = VehicleGarageService.getInstance();

    @Override
    public void execute() {
        int index = UserInputUtils.getInt("Input index of desired vehicle ==> ");
        System.out.println(VEHICLE_GARAGE_SERVICE.getCreatedDate(index));
    }
}
