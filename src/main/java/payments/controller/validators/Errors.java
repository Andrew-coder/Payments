package payments.controller.validators;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Errors {
    private Map<String, String> errors;

    public Errors() {
        errors = new HashMap<>();
    }

    public void addError(String attribute, String messageKey){
        errors.put(attribute, messageKey);
    }

    public void addErrors(Map<String, String> errors){
        this.errors.putAll(errors);
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    public Set<String> getErrorsAttributes() {
        return errors.keySet();
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}