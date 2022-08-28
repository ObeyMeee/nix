package ua.com.andromeda.homework14.garage.actions;

import ua.com.andromeda.homework10.service.TruckService;

public class SaveAll implements Command {
    @Override
    public void execute() {
        TruckService autoService = TruckService.getInstance();
        autoService.saveAll(autoService.createVehicles(10));
    }
}
