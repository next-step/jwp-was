package webserver.exceptions;

public class FailReadFileException extends WebServerException {
    public FailReadFileException() {
        super(ErrorMessage.FAIL_READ_FILE);
    }
}
