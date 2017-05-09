package payments.controller.exception;

import payments.exception.ApplicationException;

public class ControllerException extends ApplicationException {
    public ControllerException(String messageKey) {
        super(messageKey);
    }

    public ControllerException(Throwable cause, String messageKey) {
        super(cause, messageKey);
    }
}
