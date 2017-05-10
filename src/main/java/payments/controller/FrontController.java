package payments.controller;


import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(FrontController.class);
    private transient CommandHolder commandHolder = new CommandHolder();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = processRequest(request, response);
        if(!path.equals(PagesPath.REDIRECT)) {
             request.getRequestDispatcher(path).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = processRequest(request, response);
        if(!path.equals(PagesPath.FORWARD))
            response.sendRedirect(path);
    }

    public String processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String method = request.getMethod().toUpperCase();
        String path = request.getRequestURI();
        String key = method+":"+path;
        logger.debug(method + " Uri: "+ path);
        Command command = commandHolder.findCommand(key);
        return command.execute(request, response);
    }

    public void setCommandHolder(CommandHolder commandHolder) {
        this.commandHolder=commandHolder;
    }
}