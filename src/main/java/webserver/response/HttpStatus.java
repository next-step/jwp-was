package webserver.response;

/**
 * Created by hspark on 2019-08-05.
 */
public enum HttpStatus {
    OK(200), FOUND(302), NOT_FOUND(404), INTERNAL_SERVER_ERROR(500);

    private int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
