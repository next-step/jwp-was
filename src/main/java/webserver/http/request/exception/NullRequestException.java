package webserver.http.request.exception;

public class NullRequestException extends RuntimeException{
    public NullRequestException(String message) {
        super(message);
    }
}
