package payments.controller.validators;

import payments.model.dto.payment.RefillData;
import payments.utils.constants.Attributes;
import payments.utils.constants.MessageKeys;

import java.util.regex.Pattern;

public class RefillValidator implements Validator<RefillData>{
    private static final String REGEX_DATE = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
    private static final String REGEX_CARD_NUMBER = "[\\d]{16}";
    private static final String REGEX_PIN = "[\\d]{4}";
    private static final String REGEX_CVV = "[\\d]{3}";

    @Override
    public Errors validate(RefillData data) {
        Errors errors = new Errors();
        if(!Pattern.matches(REGEX_CARD_NUMBER, data.getCardNumber())){
            errors.addError(Attributes.SENDER_CARD, MessageKeys.WRONG_SENDER_CARD);
        }
        if(!Pattern.matches(REGEX_DATE, data.getExpireDate())){
            errors.addError(Attributes.EXPIRE_DATE, MessageKeys.WRONG_EXPIRE_DATE);
        }
        if(!Pattern.matches(REGEX_PIN, data.getPin())){
            errors.addError(Attributes.PIN, MessageKeys.WRONG_PIN);
        }
        if(!Pattern.matches(REGEX_CVV, data.getCvv())){
            errors.addError(Attributes.CVV, MessageKeys.WRONG_CVV);
        }
        return errors;
    }
}