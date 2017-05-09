package payments.exception;

public class ApplicationException extends RuntimeException{
    private String messageKey;

    public ApplicationException(String messageKey) {
        this.messageKey = messageKey;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(Throwable cause, String messageKey) {
        super(cause);
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
