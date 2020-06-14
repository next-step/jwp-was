package webserver.exceptions;

public class UncheckedURISyntaxException extends WebServerException {
    public UncheckedURISyntaxException(String filePath) {
        super(ErrorMessage.ILLEGAL_URI_SYNTAX, filePath);
    }
}
