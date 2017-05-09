package payments.controller.commands;

import org.apache.log4j.Logger;
import payments.controller.validators.Errors;
import payments.exception.ApplicationException;
import payments.service.exception.ServiceException;
import payments.utils.constants.Attributes;
import payments.utils.constants.LoggerMessages;
import payments.utils.constants.MessageKeys;
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
            logger.error(LoggerMessages.SERVICE_EXCEPTION_OCCURRED, exception);
            putErrorMessageInRequest(request, exception.getMessageKey());
            request.getRequestDispatcher(nextPage).forward(request, response);
        }
        catch (ApplicationException exception){
            logger.error(LoggerMessages.APPLICATION_EXCEPTION_OCCURRED, exception);
            putErrorMessageInRequest(request, exception.getMessageKey());
            request.getRequestDispatcher(PagesPath.ERROR_PAGE).forward(request,response);
        }
        catch (Exception exception){
            logger.error(LoggerMessages.UNKNOWN_ERROR_OCCURED, exception);
            putErrorMessageInRequest(request, MessageKeys.UNKNOWN_ERROR_OCCURED);
            request.getRequestDispatcher(PagesPath.ERROR_PAGE).forward(request,response);

        }
        return PagesPath.FORWARD;
    }

    public void putErrorMessageInRequest(HttpServletRequest request, String messageKey){
        Errors errors = (Errors) request.getAttribute(Attributes.ERRORS);
        if(errors==null){
            errors = new Errors();
        }
        errors.addError(Attributes.ERROR, messageKey);
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