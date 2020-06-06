package service.exceptions;

public final class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
