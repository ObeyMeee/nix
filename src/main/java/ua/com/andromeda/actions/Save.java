package ua.com.andromeda.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.cars.Auto;
import ua.com.andromeda.service.AutoService;


public class Save implements Command {

    private static final Logger LOGGER = LoggerFactory.getLogger(Save.class);
    private static final AutoService AUTO_SERVICE = ApplicationContext.getInstance().get(AutoService.class);

    @Override
    public void execute() {
        Auto randomVehicle = AUTO_SERVICE.createRandomVehicle();
        boolean isVehicleAdded = AUTO_SERVICE.save(randomVehicle);
        if (isVehicleAdded) {
            LOGGER.info("Vehicle {} was successfully created", randomVehicle);
        }
    }
}
