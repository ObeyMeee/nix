package ua.com.andromeda.actions;

import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.service.AutoService;
import ua.com.andromeda.utils.UserInputUtils;


public class Update implements Command {
    private static final AutoService AUTO_SERVICE = ApplicationContext.getInstance().get(AutoService.class);

    @Override
    public void execute() {
        String id = UserInputUtils.getString("Input id to update vehicle ==> ");
        Auto randomVehicle = AUTO_SERVICE.createRandomVehicle();
        randomVehicle.setId(id);
        AUTO_SERVICE.update(randomVehicle);
    }
}
