package ua.com.andromeda.homework14.garage.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.homework10.model.Truck;
import ua.com.andromeda.homework10.service.TruckService;


public class Save implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(Save.class);
    private static final TruckService AUTO_SERVICE = TruckService.getInstance();

    @Override
    public void execute() {
        Truck randomVehicle = AUTO_SERVICE.createRandomVehicle();
        boolean isVehicleAdded = AUTO_SERVICE.save(randomVehicle);
        if (isVehicleAdded) {
            LOGGER.info("Vehicle {} was successfully created", randomVehicle);
        }
    }
}
