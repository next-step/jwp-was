package http.response;

import http.HttpStatus;
import lombok.Getter;

@Getter
public class ResponseHeader {
    private final static String HOST = "http://localhost:8080";
    private HttpStatus httpStatus;
    private String contentType;
    private int contentLength;
    private String location;

    private ResponseHeader(HttpStatus httpStatus, String contentType, int contentLength) {
        this(httpStatus, contentType, contentLength, null);
    }

    private ResponseHeader(HttpStatus httpStatus, String contentType, int contentLength, String location) {
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.location = location;
    }

    public static ResponseHeader of(HttpStatus httpStatus, String contentType, int contentLength) {
        return new ResponseHeader(httpStatus, contentType, contentLength);
    }

    public static ResponseHeader of(HttpStatus httpStatus, String contentType, int contentLength, String location) {
        return new ResponseHeader(httpStatus, contentType, contentLength, location);
    }

    public String getLocation() {
        return HOST + this.location;
    }

    public int getStatusCode() {
        return this.httpStatus.getCode();
    }

    public String getStatusName() {
        return this.httpStatus.getName();
    }
}
