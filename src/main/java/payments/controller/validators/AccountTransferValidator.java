package payments.controller.validators;

import payments.model.dto.payment.AccountTransferData;
import payments.utils.constants.Attributes;
import payments.utils.constants.MessageKeys;
import java.util.regex.Pattern;

public class AccountTransferValidator implements Validator<AccountTransferData> {
    private static final String REGEX_CARD_NUMBER = "[\\d]{16}";
    private static final String REGEX_ACCOUNT_NUMBER = "[\\d]{14}";

    private UsreouValidator usreouValidator = new UsreouValidator();
    private MfoValidator mfoValidator = new MfoValidator();

    @Override
    public Errors validate(AccountTransferData data) {
        Errors errors = new Errors();
        errors.addErrors(usreouValidator.validate(data.getUsreou()).getErrors());
        errors.addErrors(mfoValidator.validate(data.getMfo()).getErrors());
        if(!Pattern.matches(REGEX_CARD_NUMBER, data.getSenderCard())){
            errors.addError(Attributes.SENDER_CARD, MessageKeys.WRONG_SENDER_CARD);
        }
        if(!Pattern.matches(REGEX_ACCOUNT_NUMBER, data.getAccountNumber())){
            errors.addError(Attributes.ACCOUNT_NUMBER, MessageKeys.WRONG_ACCOUNT_NUMBER);
        }
        return errors;
    }
}