package ua.com.andromeda.homework14.garage.actions;

import lombok.Getter;


@Getter
public enum Action {
    CREATE("Create vehicle", new Create()),
    INSERT("Insert vehicle into indicated position", new Insert()),
    FIND("Find by index", new Find()),
    DELETE("Delete by index", new Delete()),
    PRINT("Print all", new Print()),
    UPDATE("Update auto", new Update()),
    GET_CREATED_DATE("Get date of creation vehicle", new GetCreatedDate()),
    GET_UPDATED_DATE("Get date of update vehicle", new GetUpdatedDate()),
    GET_RESTYLING_AMOUNT("Get restyling amount", new GetRestylingAmount()),
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
