package payments.dao.exception;

import payments.exception.ApplicationException;

public class DaoException extends ApplicationException{
    public DaoException(String messageKey) {
        super(messageKey);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(Throwable cause, String messageKey) {
        super(cause, messageKey);
    }
}
