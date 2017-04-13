package payments.controller.commands;

import payments.utils.constants.PagesPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by andrew on 13.04.17.
 */
public class CardsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        return PagesPath.CARDS_PAGE;
    }
}
