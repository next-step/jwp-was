package webserver.http.domain.exception;

public class NullRequestException extends RuntimeException{
    public NullRequestException(String message) {
        super(message);
    }
}
