package model;

public class IllegalUserInputException extends RuntimeException {

    private static final String MESSAGE = "Wrong Input To Make User. field: %s, input: %s";

    public IllegalUserInputException(String field, String input) {
        super(String.format(MESSAGE, field, input));
    }
}
