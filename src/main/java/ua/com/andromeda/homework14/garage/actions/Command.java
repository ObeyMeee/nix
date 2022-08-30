package ua.com.andromeda.homework14.garage.actions;


import ua.com.andromeda.homework19.ApplicationContext;

public interface Command {
    ApplicationContext context = ApplicationContext.getInstance();

    void execute();
}
