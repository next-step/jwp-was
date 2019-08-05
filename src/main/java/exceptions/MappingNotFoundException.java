package exceptions;

public class MappingNotFoundException extends RuntimeException {

    public MappingNotFoundException() {
        super();
    }


    public MappingNotFoundException(String message) {
        super(message);
    }


    public MappingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


    public MappingNotFoundException(Throwable cause) {
        super(cause);
    }
}
