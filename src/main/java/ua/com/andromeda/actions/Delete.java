package ua.com.andromeda.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.service.AutoService;
import ua.com.andromeda.service.VehicleService;
import ua.com.andromeda.utils.UserInputUtils;

public class Delete implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(Delete.class);
    private static final VehicleService<Auto> VEHICLE_SERVICE = ApplicationContext.getInstance().get(AutoService.class);

    @Override
    public void execute() {
        String id = UserInputUtils.getString("Input id to remove vehicle ==> ");
        boolean isVehicleRemoved = VEHICLE_SERVICE.delete(id);
        if (isVehicleRemoved) {
            LOGGER.info("Vehicle was successfully removed by id = {}", id);
        }
    }
}
