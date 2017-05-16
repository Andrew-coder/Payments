package payments.utils.constants;

/**
 * This class contains all necessary messages to perform logging
 */
public class LoggerMessages {
    public static final String ERROR_PARSING_INPUT_DATE = "Error occured when parsing date...";
    public static final String SUCCESSFULL_USER_INFO_PARSE = "User's info was successfully parsed";
    public static final String SUCCESSFULL_REFILLING = "Card was successfully refilled";
    public static final String SUCCESSFULL_CARD_TRANSFER = "Card transfer was successfully done";
    public static final String SUCCESSFULL_ACCOUNT_TRANSFER = "Account transfer was successfully done";
    public static final String SUCCESSFUL_REGISTER = "Congratulations! You was successfully registered!";

    public static final String CAN_NOT_CLOSE_CONNECTION = "Can not close connection";
    public static final String CAN_NOT_BEGIN_TRANSACTION = "Can not begin transaction";
    public static final String CAN_NOT_ROLLBACK_TRANSACTION = "Can not rollback transaction";
    public static final String CAN_NOT_COMMIT_TRANSACTION = "Can not commit transaction";

    public static final String ERROR_CONNECT_TO_DATABASE = "";

    public static final String ERROR_FIND_USER_BY_CELLPHONE = "Error occurred when finding user by cellphone: ";
    public static final String ERROR_FIND_USER_BY_ID = "Error occurred when finding user by id: ";
    public static final String ERROR_FIND_ALL_USERS = "Error occurred when finding all users";
    public static final String ERROR_CREATE_NEW_USER = "Error occurred when creating new user: ";
    public static final String ERROR_UPDATE_USER = "Error occurred when updating user: ";
    public static final String ERROR_UPDATE_USER_CARDS = "Error occurred when updating user's cards";
    public static final String ERROR_DELETE_USER = "Error occurred when deleting user: ";

    public static final String ERROR_FIND_TARIFF_BY_ID = "Error occurred when finding tariff by id: ";
    public static final String ERROR_FIND_TARIFF_BY_TYPE = "Error occurred when finding tariff by type: ";
    public static final String ERROR_FIND_ALL_TARIFFS = "Error occurred when finding all tariffs";
    public static final String ERROR_UPDATE_TARIFF = "Error occurred when updating tariff: ";

    public static final String ERROR_FIND_PAYMENT_BY_ID = "Error occurred when finding payment by id: ";
    public static final String ERROR_FIND_ALL_PAYMENTS = "Error occurred when finding all payments";
    public static final String ERROR_FIND_ALL_PAYMENTS_BY_OFFSET = "Error occurred when finding all payments by offset and quantity: ";
    public static final String ERROR_RETRIEVE_PAYMENTS_COUNT = "Error occurred when retrieving payments total count";
    public static final String ERROR_CREATE_NEW_PAYMENT = "Error occurred when creating new payment: ";

    public static final String ERROR_FIND_CARD_BY_ID = "Error occurred when finding card by id: ";
    public static final String ERROR_FIND_CARD_BY_NUMBER = "Error occurred when finding card by number: ";
    public static final String ERROR_FIND_CARDS_BY_USER = "Error occurred when finding card by user: ";
    public static final String ERROR_BLOCK_CARD = "Error occurred when blocking card: ";
    public static final String ERROR_UNBLOCK_CARD = "Error occurred when unblocking card: ";
    public static final String ERROR_FIND_ALL_CARDS = "Error occurred when finding all cards";
    public static final String ERROR_FIND_ALL_BLOCK_CARDS = "Error occurred when finding all blocked cards";
    public static final String ERROR_CREATE_NEW_CARD = "Error occurred when creating new card: ";
    public static final String ERROR_UPDATE_CARD = "Error occurred when updating card: ";

    public static final String ERROR_FIND_ACCOUNT_BY_ID = "Error occurred when finding card by id: ";
    public static final String ERROR_FIND_ACCOUNT_BY_NUMBER = "Error occurred when finding account by number: ";
    public static final String ERROR_FIND_ACCOUNT_BY_CARD = "Error occurred when finding user by card: ";
    public static final String ERROR_FIND_ALL_ACCOUNT = "Error occurred when finding all accounts";
    public static final String ERROR_CREATE_ACCOUNT = "Error occurred when creating new account: ";
    public static final String ERROR_UPDATE_ACCOUNT = "Error occurred when updating account: ";

    public static final String UNKNOWN_ERROR_OCCURED = "Unknown error occurred";
    public static final String SERVICE_EXCEPTION_OCCURRED = "exception in business logic";
    public static final String APPLICATION_EXCEPTION_OCCURRED = "application exception occurred";

    public static final String ERROR_USER_ALREADY_EXISTS = "User with such login already exists!";
}