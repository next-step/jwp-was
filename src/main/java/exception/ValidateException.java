package exception;

public class ValidateException extends IllegalArgumentException {

    private static final String MESSAGE = "invalid argument: (%s)";

    public ValidateException() {
    }

    public ValidateException(String argument) {
        super(String.format(MESSAGE, argument));
    }
}
