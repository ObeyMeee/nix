package ua.com.andromeda.homework14.garage.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.model.Vehicle;
import ua.com.andromeda.homework10.service.VehicleGarageService;
import ua.com.andromeda.homework14.garage.utils.UserInputUtils;


public class Create implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(Create.class);
    private static final VehicleGarageService VEHICLE_GARAGE_SERVICE = context.get(VehicleGarageService.class);

    @Override
    public void execute()  {
        Vehicle vehicleToBeCreated = UserInputUtils.getVehicle();
        boolean isVehicleAdded = VEHICLE_GARAGE_SERVICE.add(vehicleToBeCreated);
        if (isVehicleAdded) {
            LOGGER.info("Vehicle {} was successfully created", vehicleToBeCreated);
        }
    }
}
