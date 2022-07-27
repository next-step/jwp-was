package webserver.http;

public class StatusLine {

    private Protocol protocol;
    private HttpStatus status;

    public StatusLine() {
        this.protocol = new Protocol("HTTP", "1.1");
        this.status = HttpStatus.OK;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String toString() {
        return String.format("%s %s", protocol.toString(), status.toString());
    }
}
