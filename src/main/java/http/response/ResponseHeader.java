package http.response;

import http.HttpStatus;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseHeader {
    private final static String HOST = "http://localhost:8080";
    private final static String SET_COOKIE_HEADER = "Set-Cookie";
    private final static String LOCATION_HEADER = "Location";

    private HttpStatus httpStatus;
    private String contentType;
    private int contentLength;
    private Map<String, String> customHeader = new HashMap<>();

    private ResponseHeader(HttpStatus httpStatus, String contentType, int contentLength) {
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.contentLength = contentLength;
    }

    public static ResponseHeader of(HttpStatus httpStatus, String contentType, int contentLength) {
        return new ResponseHeader(httpStatus, contentType, contentLength);
    }

    public static ResponseHeader of(HttpStatus httpStatus, String contentType, int contentLength, String location) {
        ResponseHeader header = new ResponseHeader(httpStatus, contentType, contentLength);
        header.setLocation(location);
        return header;
    }

    public int getStatusCode() {
        return this.httpStatus.getCode();
    }

    public String getStatusName() {
        return this.httpStatus.getName();
    }

    public void setCookie(String cookie) {
        customHeader.put(SET_COOKIE_HEADER, cookie);
    }

    private void setLocation(String location) {
        customHeader.put(LOCATION_HEADER, HOST + location);
    }
}
