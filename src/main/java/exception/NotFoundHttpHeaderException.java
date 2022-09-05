package exception;

public class NotFoundHttpHeaderException extends NotFoundException {

    private static final String MESSAGE = "not found http header: (%s)";

    public NotFoundHttpHeaderException(String httpHeader) {
        super(String.format(MESSAGE, httpHeader));
    }
}
