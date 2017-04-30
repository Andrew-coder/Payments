package payments.controller.validators;

import payments.utils.constants.Attributes;
import payments.utils.constants.ErrorMessages;

import java.util.regex.Pattern;

public class MfoValidator implements Validator<String> {
    private static final String REGEX_MFO = "";

    @Override
    public Errors validate(String s) {
        Errors errors = new Errors();
        if(!Pattern.matches(REGEX_MFO, s)){
            errors.addError(Attributes.MFO, ErrorMessages.WRONG_MFO);
        }
        return errors;
    }
}
