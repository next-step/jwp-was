package exception;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class WebServerException extends RuntimeException {

    public WebServerException(String message) {
        super(message);
    }

    public WebServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
