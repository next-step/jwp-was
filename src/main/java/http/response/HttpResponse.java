package http.response;

import http.common.ContentType;
import http.common.Cookies;
import http.common.HeaderField;
import http.common.HeaderFieldName;
import org.apache.logging.log4j.util.Strings;
import webserver.exceptions.StatusCodeNotFoundException;

import java.util.Optional;

public class HttpResponse {
    private StatusCode statusCode;
    private final ResponseHeader header;
    private final Cookies cookies;
    private byte[] responseBody;

    public HttpResponse() {
        statusCode = StatusCode.OK;
        header = new ResponseHeader();
        cookies = new Cookies();
        responseBody = new byte[0];
    }

    public int getStatusCode() {
        return Optional.ofNullable(statusCode)
                .orElseThrow(StatusCodeNotFoundException::new)
                .getCode();
    }

    public String getStatusMessage() {
        return Optional.ofNullable(statusCode)
                .orElseThrow(StatusCodeNotFoundException::new)
                .getMessage();
    }

    public void addCookie(String cookieName, String cookieValue) {
        this.cookies.addCookie(cookieName, cookieValue);
    }

    public Cookies getCookie() {
        return cookies;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public Optional<String> getHeader(String headerName) {
        return header.getValue(headerName);
    }

    public Optional<String> getHeader(HeaderFieldName headerName) {
        return header.getValue(headerName);
    }

    public byte[] getBody() {
        return responseBody;
    }

    public void setContentType(ContentType contentType) {
        header.addHeader(new HeaderField(HeaderFieldName.CONTENT_TYPE, contentType.getValue()));
    }

    public void setBody(String bodyStr) {
        setBody(bodyStr.getBytes());
    }

    public void setBody(byte[] body) {
        header.addHeader(new HeaderField(HeaderFieldName.CONTENT_LENGTH, String.valueOf(body.length)));
        this.responseBody = body;
    }

    public void sendRedirect(String redirectUrl) {
        this.statusCode = StatusCode.FOUND;
        header.addHeader(new HeaderField(HeaderFieldName.LOCATION, redirectUrl));
    }

}
