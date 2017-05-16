package payments.dao.exception;

/**
 * this class represents custom exception for dao layer
 */

import payments.exception.ApplicationException;

public class DaoException extends ApplicationException{
    public DaoException(String messageKey) {
        super(messageKey);
    }

    public DaoException(String message, String messageKey) {
        super(message, messageKey);
    }

    public DaoException(Throwable cause, String messageKey) {
        super(cause, messageKey);
    }
}
