package exception;

public class NotFoundMediaTypeException extends NotFoundException {

    private static final String MESSAGE = "not found media type: (%s)";

    public NotFoundMediaTypeException(String mediaType) {
        super(String.format(MESSAGE, mediaType));
    }
}
