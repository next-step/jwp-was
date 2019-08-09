package webserver.exceptions;

public class MappingNotFoundException extends Exception {
	private static final long serialVersionUID = 7107452393139765620L;

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
