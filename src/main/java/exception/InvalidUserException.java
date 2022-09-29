package exception;

public class InvalidUserException extends RuntimeException {

    private static final String MESSAGE = "invalid user id: (%s)";

    public InvalidUserException(String userId) {
        super(String.format(MESSAGE, userId));
    }
}
