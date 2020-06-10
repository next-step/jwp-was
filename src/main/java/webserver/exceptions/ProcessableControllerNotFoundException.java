package webserver.exceptions;

public class ProcessableControllerNotFoundException extends WebServerException {
    public ProcessableControllerNotFoundException(String path) {
        super(ErrorMessage.PROCESSABLE_CONTROLLER_NOT_FOUND, path);
    }
}
