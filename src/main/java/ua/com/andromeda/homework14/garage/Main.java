package ua.com.andromeda.homework14.garage;

import ua.com.andromeda.homework14.garage.actions.Action;
import ua.com.andromeda.homework14.garage.actions.Command;
import ua.com.andromeda.homework14.garage.utils.UserInputUtils;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Command command;
        do {
            Action[] actions = Action.values();

            List<String> actionDescriptions = Arrays.stream(actions)
                    .map(action -> action.getActionDescription())
                    .toList();

            int userInput = UserInputUtils.selectGivenActions(actions.length, actionDescriptions);
            command = actions[userInput].execute();
        } while (command != null);
    }
}
