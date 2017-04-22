package payments.controller.validators;

public interface Validator<T> {
    Errors validate(T t);
}
