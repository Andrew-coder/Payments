package payments.controller.validators;

import payments.model.dto.RegisterData;
import payments.utils.constants.Attributes;
import payments.utils.constants.MessageKeys;

import java.util.regex.Pattern;

public class UserValidator implements Validator<RegisterData> {
    private static final String REGEX_NAME="[A-Z]{1}[a-z]{1,}";
    private static  final String REGEX_PHONE="^(\\\\+380|0)([0-9]{9})$";
    private static final String REGEX_PASSWORD = "[A-Za-z0-9]{4,200}";
    private static final String REGEX_DATE = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    @Override
    public Errors validate(RegisterData data) {
        Errors results = new Errors();
        if(!Pattern.matches(REGEX_NAME, data.getName())){
            results.addError(Attributes.USER_NAME, MessageKeys.WRONG_USER_NAME);
        }
        if(!Pattern.matches(REGEX_NAME, data.getSurname())){
            results.addError(Attributes.USER_SURNAME, MessageKeys.WRONG_USER_SURNAME);
        }
        if(!Pattern.matches(REGEX_PHONE, data.getCellphone())){
            results.addError(Attributes.USER_CELLPHONE, MessageKeys.WRONG_USER_CELLPHONE);
        }
        if(!Pattern.matches(REGEX_PASSWORD, data.getPassword())){
            results.addError(Attributes.USER_PASSWORD, MessageKeys.WRONG_USER_PASSWORD);
        }
        if(!Pattern.matches(REGEX_DATE, data.getBirthDate())){
            results.addError(Attributes.USER_DATE, MessageKeys.WRONG_USER_DATE);
        }
        return results;
    }
}