package ua.com.andromeda.homework14.garage.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.service.VehicleGarageService;
import ua.com.andromeda.homework14.garage.utils.UserInputUtils;

public class Delete implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(Delete.class);
    private static final VehicleGarageService VEHICLE_GARAGE_SERVICE =
            context.get(VehicleGarageService.class);

    @Override
    public void execute() {
        int index = UserInputUtils.getInt("Input index to remove vehicle ==> ");
        boolean isVehicleRemoved = VEHICLE_GARAGE_SERVICE.remove(index);
        if (isVehicleRemoved) {
            LOGGER.info("Vehicle was successfully removed by index = {}", index);
        }
    }
}
