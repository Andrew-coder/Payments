package payments.utils.constants;

public final class ErrorMessages {
    public static final String USER_ALREADY_EXISTS = "User with such login already exists!";
    public static final String UNKNOWN_ERROR_OCCURED = "Unknown error occured";

    public static final String WRONG_USER_NAME = "Wrong user name";
    public static final String WRONG_USER_SURNAME = "Wrong user surname";
    public static final String WRONG_USER_PASSWORD = "Wrong user password (at least 4 characters)";
    public static final String WRONG_USER_EMAIL = "Wrong user email";
    public static final String WRONG_USER_DATE = "Wrong user date! Date format (yyyy-mm-dd)";
    public static final String WRONG_LOGIN_DATA = "Wrong user login or password";

    public static final String WRONG_QUERY_PARAMETER = "Wrong query parameter";
    public static final String WRONG_PARAMETER = "Wrong parameter";

    public static final String WRONG_USREOU = "Wrong usreou";
    public static final String WRONG_MFO = "Wrong mfo";
    public static final String WRONG_SENDER_CARD = "Wrong sender's card number";
    public static final String WRONG_RECIPIENT_CARD = "Wrong recipient's card number";
    public static final String WRONG_SUM = "Wrong sum";
    public static final String WRONG_ACCOUNT_NUMBER = "Wrong bank account number";

    public static final String CARD_NOT_EXIST = "Card with such id doesn't exist";
    public static final String ACCOUNT_NOT_EXIST = "Bank account with such number doesn't exist";
    public static final String WRONG_CARD_DATA = "Wrong card data!";

    public static final String NOT_ENOUGH_MONEY = "Not enough money for payment!";
    public static final String CARD_IS_BLOCKED = "This card is currently blocked. To unblock it connect with admin";
    public static final String TRANSFER_TO_THE_SAME_ACCOUNT = "It is not possible to transfer money to the same account";
}
