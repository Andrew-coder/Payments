package payments.exception;

/**
 * this class represents custom application exception
 */
public class ApplicationException extends RuntimeException{
    /**
     * bundle key to error text message
     */
    private final String messageKey;

    public ApplicationException(String messageKey) {
        this.messageKey = messageKey;
    }

    public ApplicationException(Throwable cause, String messageKey) {
        super(cause);
        this.messageKey = messageKey;
    }

    public ApplicationException(String message, String messageKey) {
        super(message);
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
