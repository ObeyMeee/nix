package ua.com.andromeda.actions;

import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.service.AutoService;

public class SaveAll implements Command {
    @Override
    public void execute() {
        AutoService autoService = ApplicationContext.getInstance().get(AutoService.class);
        autoService.saveAll(autoService.createVehicles(10));
    }
}
