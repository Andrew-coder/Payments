package payments.controller.validators;

import payments.model.dto.payment.RefillData;

public class RefillValidator implements Validator<RefillData>{
    @Override
    public Errors validate(RefillData data) {
        Errors errors = new Errors();
        return errors;
    }
}