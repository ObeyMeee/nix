package ua.com.andromeda.homework14.garage.actions;

import ua.com.andromeda.homework10.service.TruckService;
import ua.com.andromeda.homework14.garage.utils.UserInputUtils;


public class Update implements Command {
    private static final TruckService AUTO_SERVICE = TruckService.getInstance();

    @Override
    public void execute() {
        String id = UserInputUtils.getString("Input id to update vehicle ==> ");
        AUTO_SERVICE.update(AUTO_SERVICE.createRandomVehicle(), id);
    }
}
