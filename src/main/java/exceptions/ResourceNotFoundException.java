package exceptions;

import java.io.IOException;

public class ResourceNotFoundException extends IOException {

    public ResourceNotFoundException() {
        super();
    }


    public ResourceNotFoundException(String message) {
        super(message);
    }


    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
