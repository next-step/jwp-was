package exception;

public class NotFoundUserException extends NotFoundException {

    private static final String MESSAGE = "not found user id: (%s)";

    public NotFoundUserException(String userId) {
        super(String.format(MESSAGE, userId));
    }
}
