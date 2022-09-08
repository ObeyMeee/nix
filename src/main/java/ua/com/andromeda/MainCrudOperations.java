package ua.com.andromeda;

import ua.com.andromeda.actions.Action;
import ua.com.andromeda.actions.Command;
import ua.com.andromeda.utils.UserInputUtils;

import java.util.Arrays;
import java.util.List;

public class MainCrudOperations {
    public static void main(String[] args) {
        Command command;
        do {
            Action[] actions = Action.values();

            List<String> actionDescriptions = Arrays.stream(actions)
                    .map(Action::getActionDescription)
                    .toList();

            int userInput = UserInputUtils.selectGivenActions(actions.length, actionDescriptions);
            command = actions[userInput].execute();
        } while (command != null);
    }
}
