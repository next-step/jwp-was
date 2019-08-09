package webserver.exceptions;

public class CookieParsingException extends RuntimeException{
	
	public static String DEFAULT_MESSAGE = "Cookie parsing 에서 예외가 발생하였습니다.";
	public static String COOKIE_SPLIT_INVALID_LENGTH = "Cookie Split이 잘못된 길이 입니다.";
	
	public CookieParsingException() {
		super(DEFAULT_MESSAGE);
    }

    public CookieParsingException(String message) {
        super(message);
    }

    public CookieParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieParsingException(Throwable cause) {
        super(cause);
    }

}
