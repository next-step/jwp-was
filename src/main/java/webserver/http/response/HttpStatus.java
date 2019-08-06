package webserver.http.response;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public enum HttpStatus {
    OK(200, "OK"),
    REDIRECT(302, "Found"),
    BAD_REQUEST(400, "Bad Request"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    ;

    private final int code;
    private final String reasonPhrase;

    HttpStatus(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public int getCode() {
        return code;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
