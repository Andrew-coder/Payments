package payments.controller.commands;

import payments.controller.validators.Errors;
import payments.utils.constants.Attributes;
import payments.utils.constants.MessageKeys;
import payments.utils.constants.PagesPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PageNotFoundCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Errors errors = new Errors();
        errors.addError(Attributes.ERROR, MessageKeys.URL_NOT_FOUND);
        request.setAttribute(Attributes.ERRORS, errors);
        return PagesPath.ERROR_PAGE;
    }
}