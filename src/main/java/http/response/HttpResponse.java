package http.response;

import http.common.ContentType;
import http.common.Cookies;
import http.common.HeaderField;
import http.common.HeaderFieldName;
import webserver.exceptions.StatusCodeNotFoundException;

import java.util.Optional;

public class HttpResponse {
    private Optional<StatusCode> statusCode;
    private final ResponseHeader header;
    private final Cookies cookies;
    private byte[] responseBody;

    public HttpResponse() {
        statusCode = Optional.empty();
        header = new ResponseHeader();
        cookies = new Cookies();
        responseBody = new byte[0];
    }

    public int getStatusCode() {
        return statusCode
                .orElseThrow(StatusCodeNotFoundException::new)
                .getCode();
    }

    public String getStatusMessage() {
        return statusCode
                .orElseThrow(StatusCodeNotFoundException::new)
                .getMessage();
    }

    public void addCookie(String cookieName, String cookieValue) {
        this.cookies.addCookie(cookieName, cookieValue);
    }

    public Cookies getCookie() {
        return cookies;
    }

    public void response302(String locationUrl) {
        this.statusCode = Optional.of(StatusCode.FOUND);
        HeaderField locationHeader = new HeaderField(HeaderFieldName.LOCATION, locationUrl);
        header.addHeader(locationHeader);
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public String getHeader(String headerName) {
        return header.getValue(headerName);
    }

    public String getHeader(HeaderFieldName headerName) {
        return header.getValue(headerName);
    }

    public byte[] getBody() {
        return responseBody;
    }

    public void addCookiePath(String path) {
        this.cookies.setPath(path);
    }

    public void response200(ContentType contentType, byte[] body) {
        this.statusCode = Optional.of(StatusCode.OK);
        final String contentTypeValue = contentType.getValue();

        header.addHeader(new HeaderField(HeaderFieldName.CONTENT_TYPE, contentTypeValue));
        header.addHeader(new HeaderField(HeaderFieldName.CONTENT_LENGTH, String.valueOf(body.length)));
        this.responseBody = body;
    }

}
