package ua.com.andromeda.homework14.garage.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.service.TruckService;
import ua.com.andromeda.homework10.service.VehicleService;
import ua.com.andromeda.homework14.garage.utils.UserInputUtils;

public class Delete implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(Delete.class);
    private static final VehicleService<Truck> VEHICLE_SERVICE = TruckService.getInstance();

    @Override
    public void execute() {
        String id = UserInputUtils.getString("Input id to remove vehicle ==> ");
        boolean isVehicleRemoved = VEHICLE_SERVICE.delete(id);
        if (isVehicleRemoved) {
            LOGGER.info("Vehicle was successfully removed by id = {}", id);
        }
    }
}
