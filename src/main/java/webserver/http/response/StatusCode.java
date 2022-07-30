package webserver.http.response;

public enum StatusCode {

    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),

    MOVED_PERMANENTLY(301),
    FOUND(302),
    NOT_MODIFIED(304),
    TEMPORARRY_REDIRECT(307),

    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),

    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILVABLE(503);

    private final int status;

    StatusCode(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
