package payments.controller.validators;

/**
 * Generic Validator interface which should return dto object with errors
 * @param <T>
 */
public interface Validator<T> {
    /**
     * main methods which validates input data
     * @param t
     * @return dto object with errors
     */
    Errors validate(T t);
}
