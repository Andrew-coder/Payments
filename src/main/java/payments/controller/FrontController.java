package payments.controller;


import payments.controller.commands.*;
import payments.utils.constants.PagesPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrontController extends HttpServlet{
    private Map<String, Command> commands = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = processRequest(request, response);
        if(!path.equals(PagesPath.REDIRECT)) {
             request.getRequestDispatcher(path).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public String processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String method = request.getMethod().toUpperCase();
        String path = request.getRequestURI();
        String key = method+":"+path;
        Command command = commands.getOrDefault(key, new PageNotFoundCommand());
        return command.execute(request, response);
    }

    @Override
    public void init() throws ServletException {
        commands.put("GET:/home", new HomeCommand());
        commands.put("GET:/login", new LoginCommand());
        commands.put("GET:/cards", new CardsCommand());
        commands.put("GET:/cards/id", new CardsManagmentCommand());
        commands.put("GET:/admin", new AdminHomeCommand());
        commands.put("GET:/payments", new PaymentsCommand());
        commands.put("GET:/payments/id", new PaymentsInfoCommand());
    }
}