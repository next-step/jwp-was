package model.http;

public class StatusLine {
    private HttpVersion httpVersion = HttpVersion.HTTP1_1;
    private StatusCode statusCode;

    private StatusLine(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public static StatusLine of(StatusCode statusCode) {
        return new StatusLine(statusCode);
    }

    public String print() {
        return httpVersion.getVersion() + " " + statusCode.getCode() + " " + statusCode.getReasonPhase();
    }
}
