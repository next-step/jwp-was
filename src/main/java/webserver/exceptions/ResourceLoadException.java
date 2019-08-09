package webserver.exceptions;

public class ResourceLoadException extends RuntimeException {

    public ResourceLoadException() {
        super();
    }


    public ResourceLoadException(String message) {
        super(message);
    }


    public ResourceLoadException(String message, Throwable cause) {
        super(message, cause);
    }


    public ResourceLoadException(Throwable cause) {
        super(cause);
    }
}
