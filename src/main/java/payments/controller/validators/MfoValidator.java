package payments.controller.validators;

import payments.utils.constants.Attributes;
import payments.utils.constants.MessageKeys;

import java.util.regex.Pattern;

public class MfoValidator implements Validator<String> {
    private static final String REGEX_MFO = "[\\d]{6}";

    @Override
    public Errors validate(String s) {
        Errors errors = new Errors();
        if(!validateMfoCodeByRegex(s)){
            errors.addError(Attributes.MFO, MessageKeys.WRONG_MFO);
            return errors;
        }
        int[] digits = convertStringToArray(s);
        int lastDigit = digits[digits.length-1];
        if(lastDigit!= calculateСontrolCategory(digits)){
            errors.addError(Attributes.MFO, MessageKeys.WRONG_MFO);
        }
        return errors;
    }

    private boolean validateMfoCodeByRegex(String mfo){
        if(!Pattern.matches(REGEX_MFO, mfo)){
            return false;
        }
        return true;
    }

    private int[] convertStringToArray(String str){
        return str.chars().map(x -> x - '0')
                .toArray();
    }

    private int lastDigit(long number) {
        return (int)number % 10;
    }

    private long calculateСontrolCategory(int[] digits){
        long expressionValue = 7*(digits[0]+ digits[1]*3+digits[2]*7+digits[3]+digits[4]*3);
        return expressionValue%10;
    }
}