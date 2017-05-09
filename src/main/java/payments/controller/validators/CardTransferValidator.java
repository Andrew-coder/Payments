package payments.controller.validators;

import payments.model.dto.payment.CardTransferData;
import payments.utils.constants.Attributes;
import payments.utils.constants.MessageKeys;

import java.util.regex.Pattern;

public class CardTransferValidator implements Validator<CardTransferData>{
    private static final String REGEX_CARD_NUMBER = "[\\d]{16}";

    @Override
    public Errors validate(CardTransferData data) {
        Errors errors = new Errors();
        if(!Pattern.matches(REGEX_CARD_NUMBER, data.getSenderCard())){
            errors.addError(Attributes.SENDER_CARD, MessageKeys.WRONG_SENDER_CARD);
        }
        if(!Pattern.matches(REGEX_CARD_NUMBER, data.getRecipientCard())){
            errors.addError(Attributes.RECIPIENT_CARD, MessageKeys.WRONG_RECIPIENT_CARD);
        }
        return errors;
    }
}
