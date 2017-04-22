package payments.controller.commands;

import org.apache.log4j.Logger;
import payments.controller.validators.Errors;
import payments.service.exception.ServiceException;
import payments.utils.constants.Attributes;
import payments.utils.constants.ErrorMessages;
import payments.utils.constants.PagesPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class CommandExecutor implements Command {
    private static final Logger logger = Logger.getLogger(CommandExecutor.class);
    private final String nextPage;

    protected CommandExecutor(String nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            return performExecute(request, response);

        }
        catch (ServiceException exception){
            logger.error(exception.getMessage());
            putErrorMessageInRequest(request, exception.getMessage());
            request.getRequestDispatcher(nextPage).forward(request, response);
        }
        catch (Exception exception){
            logger.error(exception.getMessage());
            putErrorMessageInRequest(request, ErrorMessages.UNKNOWN_ERROR_OCCURED);
            request.getRequestDispatcher(PagesPath.ERROR_PAGE).forward(request,response);
        }
        return PagesPath.FORWARD;
    }

    public void putErrorMessageInRequest(HttpServletRequest request, String message){
        Errors errors = (Errors) request.getAttribute(Attributes.ERRORS);
        if(errors==null){
            errors = new Errors();
        }
        errors.addError(Attributes.ERROR, message);
        request.setAttribute(Attributes.ERRORS, errors);
    }

    public abstract String performExecute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
