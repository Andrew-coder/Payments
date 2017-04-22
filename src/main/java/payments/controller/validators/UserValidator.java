package payments.controller.validators;

import payments.model.dto.RegisterData;
import payments.utils.constants.Attributes;
import payments.utils.constants.ErrorMessages;

import java.util.regex.Pattern;

public class UserValidator implements Validator<RegisterData> {
    private static final String REGEX_NAME="[A-Z]{1}[a-z]{1,}";
    private static  final String REGEX_MAIL="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String REGEX_PASSWORD = "[A-Za-z0-9]{4,200}";
    private static final String REGEX_DATE = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    @Override
    public Errors validate(RegisterData data) {
        Errors results = new Errors();
        if(!Pattern.matches(REGEX_NAME, data.getName())){
            results.addError(Attributes.USER_NAME, ErrorMessages.WRONG_USER_NAME);
        }
        if(!Pattern.matches(REGEX_NAME, data.getSurname())){
            results.addError(Attributes.USER_SURNAME, ErrorMessages.WRONG_USER_SURNAME);
        }
        if(!Pattern.matches(REGEX_MAIL, data.getEmail())){
            results.addError(Attributes.USER_EMAIL, ErrorMessages.WRONG_USER_EMAIL);
        }
        if(!Pattern.matches(REGEX_PASSWORD, data.getPassword())){
            results.addError(Attributes.USER_PASSWORD, ErrorMessages.WRONG_USER_PASSWORD);
        }
        if(!Pattern.matches(REGEX_DATE, data.getBirthDate())){
            results.addError(Attributes.USER_DATE, ErrorMessages.WRONG_USER_DATE);
        }
        return results;
    }
}
