package webserver.http.domain.request.exception;

public class NullRequestException extends RuntimeException{
    public NullRequestException(String message) {
        super(message);
    }
}
