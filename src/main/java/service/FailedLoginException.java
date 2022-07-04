package service;

public class FailedLoginException extends Throwable {

    private static final String MESSAGE = "Failed To Login. Check %s";

    public FailedLoginException(String key) {
        super(String.format(MESSAGE, key));
    }
}
