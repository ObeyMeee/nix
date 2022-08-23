package ua.com.andromeda.homework14.garage.actions;

import ua.com.andromeda.homework10.service.VehicleGarageService;
import ua.com.andromeda.homework14.garage.utils.UserInputUtils;


public class GetRestylingAmount implements Command {
    private static final VehicleGarageService VEHICLE_GARAGE_SERVICE = context.get(VehicleGarageService.class);

    @Override
    public void execute() {
        int index = UserInputUtils.getInt("Input index of desired vehicle ==> ");
        int restylingAmount = VEHICLE_GARAGE_SERVICE.getRestylingAmount(index);
        System.out.println("Amount ==> " + restylingAmount);
    }
}
