package webserver;

public class TaskRejectedException extends Exception {
    public TaskRejectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
