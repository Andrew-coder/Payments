package payments.service.exception;

import payments.exception.ApplicationException;

/**
 * this class represents custom exception for service layer
 */
public class ServiceException extends ApplicationException {
    public ServiceException(String messageKey) {
        super(messageKey);
    }

    public ServiceException(Throwable cause, String messageKey) {
        super(cause, messageKey);
    }
}
