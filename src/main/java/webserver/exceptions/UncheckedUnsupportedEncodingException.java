package webserver.exceptions;

public class UncheckedUnsupportedEncodingException extends WebServerException {
    public UncheckedUnsupportedEncodingException(String encoder) {
        super(ErrorMessage.UNSSUPORTED_ENCODING, encoder);
    }
}
