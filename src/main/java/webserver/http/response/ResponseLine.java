package webserver.http.response;

import webserver.http.request.Protocol;
import webserver.http.request.Version;

public class ResponseLine {
    private Protocol protocol;

    private Version version;

    private HttpStatus httpStatus;

    public ResponseLine(Protocol protocol, Version version) {
        this.protocol = protocol;
        this.version = version;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void ok() {
        httpStatus = HttpStatus.OK;
    }

    public void redirect() {
        httpStatus = HttpStatus.REDIRECT;
    }

    public String toString() {
        return String.format("%s/%s %s", protocol.toString(), version.getValue(), httpStatus.statusMessage);
    }
}
