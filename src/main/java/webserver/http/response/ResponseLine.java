package webserver.http.response;

import webserver.http.HttpStatus;
import webserver.http.Protocol;
import webserver.http.Type;
import webserver.http.Version;

import java.util.Objects;

public class ResponseLine {

    private final Protocol protocol;
    private final HttpStatus httpStatus;

    public ResponseLine(HttpStatus httpStatus) {
        this.protocol = new Protocol(Type.HTTP, Version.VERSION1_1);
        this.httpStatus = httpStatus;
    }

    public static ResponseLine ok() {
        return new ResponseLine(HttpStatus.OK);
    }

    public static ResponseLine redirect() {
        return new ResponseLine(HttpStatus.FOUND);
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseLine that = (ResponseLine) o;
        return Objects.equals(protocol, that.protocol) && httpStatus == that.httpStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, httpStatus);
    }

    @Override
    public String toString() {
        return protocol + " " + httpStatus;
    }
}
