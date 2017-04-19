package payments.controller;

import payments.controller.commands.AdminHomeCommand;
import payments.controller.commands.Command;
import payments.controller.commands.HomeCommand;
import payments.controller.commands.PageNotFoundCommand;
import payments.controller.commands.login.LoginCommand;
import payments.controller.commands.login.LoginSubmitCommand;
import payments.controller.commands.user.CardsCommand;
import payments.controller.commands.user.CardsManagmentCommand;
import payments.controller.commands.user.PaymentsCommand;
import payments.controller.commands.user.PaymentsInfoCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandHolder {
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
        commands.put("GET:/cards", new CardsCommand());
        commands.put("GET:/cards/id", new CardsManagmentCommand());
        commands.put("GET:/admin", new AdminHomeCommand());
        commands.put("GET:/payments", new PaymentsCommand());
        commands.put("GET:/payments/id", new PaymentsInfoCommand());
    }

    private String removeAllNumbersFromUrl(String url){
        url = url.replaceAll("/\\d+(?=/|$)", "/id");
        return url;
    }
}
