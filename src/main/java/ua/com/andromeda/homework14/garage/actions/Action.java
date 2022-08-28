package ua.com.andromeda.homework14.garage.actions;

import lombok.Getter;


@Getter
public enum Action {
    CREATE_DB_TABLES("Create database tables", new CreateDbTables()),
    SAVE("Save random vehicle", new Save()),
    SAVE_ALL("Save 10 random vehicles", new SaveAll()),
    FIND("Find by id", new Find()),
    DELETE("Delete by id", new Delete()),
    PRINT("Print all", new Print()),
    UPDATE("Update auto", new Update()),
    EXIT("Exit", null);

    private final String actionDescription;
    private final Command command;

    Action(String actionDescription, Command command) {
        this.actionDescription = actionDescription;
        this.command = command;
    }

    public Command execute() {
        if (command != null) {
            command.execute();
        }
        return command;
    }
}
