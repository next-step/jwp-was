package controller;

public class ResourceException extends RuntimeException {

    private static final String MESSAGE = "Fail To Load Resouce : %s";

    public ResourceException(String resourceName) {
        super(String.format(MESSAGE, resourceName));
    }
}
