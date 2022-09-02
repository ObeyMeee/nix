package ua.com.andromeda.actions;

import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.service.AutoService;
import ua.com.andromeda.service.VehicleService;

public class Print implements Command {
    private static final VehicleService<Auto> VEHICLE_SERVICE = ApplicationContext.getInstance().get(AutoService.class);

    @Override
    public void execute() {
        VEHICLE_SERVICE.printAll();
    }
}
