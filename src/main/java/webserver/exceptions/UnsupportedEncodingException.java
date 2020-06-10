package webserver.exceptions;

public class UnsupportedEncodingException extends WebServerException {
    public UnsupportedEncodingException(String encoder) {
        super(ErrorMessage.UNSSUPORTED_ENCODING, encoder);
    }
}
