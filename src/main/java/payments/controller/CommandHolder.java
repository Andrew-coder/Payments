package payments.controller;

import payments.controller.commands.*;
import payments.controller.commands.login.LoginCommand;
import payments.controller.commands.login.LoginSubmitCommand;
import payments.controller.commands.user.*;

import java.util.HashMap;
import java.util.Map;

public class CommandHolder {
    public static final String NUMBER_BETWEEN_SLASHES_PATTERN = "/\\d+(?=/|$)";
    private Map<String, Command> commands = new HashMap<>();

    public CommandHolder() {
        fillCommands();
    }

    Command findCommand(String key){
        String convertedKey = removeAllNumbersFromUrl(key);
        return commands.getOrDefault(convertedKey, new PageNotFoundCommand());
    }

    private void fillCommands(){
        commands.put("GET:/home", new HomeCommand());
        commands.put("GET:/login", new LoginCommand());
        commands.put("POST:/login", new LoginSubmitCommand());
        commands.put("GET:/logout", new LogoutCommand());
        commands.put("POST:/register", new RegisterSubmitCommand());
        commands.put("GET:/cards", new CardsCommand());
        commands.put("GET:/cards/id", new CardsManagmentCommand());
        commands.put("GET:/admin", new AdminHomeCommand());
        commands.put("GET:/payments", new PaymentsCommand());
        commands.put("GET:/payments/id", new PaymentsInfoCommand());
    }

    private String removeAllNumbersFromUrl(String url){
        return url.replaceAll(NUMBER_BETWEEN_SLASHES_PATTERN, "/id");
    }
}