package exception;

public class NotFoundAttributeException extends NotFoundException {

    private static final String MESSAGE = "not found attribute: (%s)";
    public NotFoundAttributeException(String name) {
        super(String.format(MESSAGE, name));
    }
}
