package payments.controller.commands;

import org.apache.log4j.Logger;
import payments.controller.exception.ControllerException;
import payments.controller.validators.Errors;
import payments.dao.exception.DaoException;
import payments.service.exception.ServiceException;
import payments.utils.constants.Attributes;
import payments.utils.constants.ErrorMessages;
import payments.utils.constants.PagesPath;
import payments.utils.extractors.RequestParamExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public abstract class CommandExecutor implements Command {
    private static final Logger logger = Logger.getLogger(CommandExecutor.class);
    private final String nextPage;
    private RequestParamExtractor paramExtractor = new RequestParamExtractor();
    private static final int DEFAULT_QUANTITY_VALUE=10;
    private static final int DEFAULT_OFFSET_VALUE = 0;

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
            putErrorMessageInRequest(request, exception.getMessage());
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

    protected int getLimitValueOrDefault(HttpServletRequest request){
        return Optional.ofNullable(paramExtractor.extractPaginParam(request, Attributes.LIMIT))
                .orElse(DEFAULT_QUANTITY_VALUE);
    }

    protected int getOffsetValueOrDefault(HttpServletRequest request, int quantity){
        return Optional.ofNullable(paramExtractor.extractPaginParam(request, Attributes.OFFSET))
                .map(page->(page-1)*quantity)
                .orElse(DEFAULT_OFFSET_VALUE);
    }

    protected int calculateOverallPagesCount(int limit, int totalCount){
        return (int)Math.ceil((totalCount+0.0)/limit);
    }
}