package payments.controller.validators;

import payments.utils.constants.Attributes;
import payments.utils.constants.MessageKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class UsreouValidator implements Validator<String>{
    private static final String REGEX_USREOU = "[\\d]{8}";

    private int MIN_RANGE_VALUE = 30000000;
    private int MAX_RANGE_VALUE = 60000000;
    private int DIVIDER = 11;

    private final Map<Integer, Integer> firstRangeWeightFactors = new HashMap<>();
    private final Map<Integer, Integer> secondRangeWeightFactors = new HashMap<>();
    {
        firstRangeWeightFactors.put(1,1);
        firstRangeWeightFactors.put(2,2);
        firstRangeWeightFactors.put(3,3);
        firstRangeWeightFactors.put(4,4);
        firstRangeWeightFactors.put(5,5);
        firstRangeWeightFactors.put(6,6);
        firstRangeWeightFactors.put(7,7);

        secondRangeWeightFactors.put(1,7);
        secondRangeWeightFactors.put(2,1);
        secondRangeWeightFactors.put(3,2);
        secondRangeWeightFactors.put(4,3);
        secondRangeWeightFactors.put(5,4);
        secondRangeWeightFactors.put(6,5);
        secondRangeWeightFactors.put(7,6);
    }

    @Override
    public Errors validate(String s) {
        Errors errors = new Errors();
        if(!validateUsreouCodeByRegex(s)){
            errors.addError(Attributes.USREOU, MessageKeys.WRONG_USREOU);
            return errors;
        }
        long usreou = extractNumberFromString(s);
        int[] digits = convertStringToArray(s);
        long controlSum=0;
        Map<Integer, Integer> weightFactors = defineWeightFactors(usreou);
        controlSum = calculateControlSum(weightFactors, digits);
        long remainder = controlSum%DIVIDER;
        if(remainder>10) {
            controlSum = recalculateControlSum(weightFactors, digits);
            remainder = controlSum%DIVIDER;
        }
        int lastDigit = digits[digits.length-1];
        if(remainder!=lastDigit){
            errors.addError(Attributes.USREOU, MessageKeys.WRONG_USREOU);
        }
        return errors;
    }

    private Map<Integer, Integer> defineWeightFactors(long usreou){
        if(usreou<MIN_RANGE_VALUE || usreou>MAX_RANGE_VALUE){
            return firstRangeWeightFactors;
        }
        return secondRangeWeightFactors;
    }

    private long calculateControlSum(Map<Integer, Integer> weightFactors, int[] digits){
        return IntStream.range(0, digits.length-1)
                .map(i -> digits[i]*weightFactors.get(i+1)).sum();
    }

    private long recalculateControlSum(Map<Integer, Integer> weightFactors, int[] digits){
        return IntStream.range(0, digits.length-1)
                .map(i -> digits[i]*(weightFactors.get(i+1)+2)).sum();
    }

    private boolean validateUsreouCodeByRegex(String usreou){
        if(!Pattern.matches(REGEX_USREOU, usreou)){
            return false;
        }
        return true;
    }

    private int[] convertStringToArray(String str){
        return str.chars().map(x -> x - '0')
                .toArray();
    }

    private long extractNumberFromString(String str){
        return Long.parseLong(str);
    }
}
