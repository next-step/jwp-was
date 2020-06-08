package http.response;

import http.Cookies;
import http.Header;
import http.Headers;

public class HttpResponse {

    private StatusCode statusCode;
    private final Headers headers;
    private final Cookies cookies;
    private byte[] responseBody;

    public HttpResponse() {
        headers = new Headers();
        cookies = new Cookies();
        responseBody = new byte[0];
    }

    public int getStatusCode() {
        return statusCode.getCode();
    }

    public String getStatusMessage() {
        return statusCode.getMessage();
    }

    public void addCookie(String cookieName, String cookieValue) {
        this.cookies.addCookie(cookieName, cookieValue);
    }

    public Cookies getCookie() {
        return cookies;
    }

    public void response302(String locationUrl) {
        this.statusCode = StatusCode.FOUND;
        Header locationHeader = new Header("Location", locationUrl);
        headers.addHeader(locationHeader);
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getHeader(String headerName) {
        return headers.getValue(headerName);
    }

    public byte[] getBody() {
        return responseBody;
    }

    public void response200HTML(byte[] htmlFile) {
        this.statusCode = StatusCode.OK;
        headers.addHeader(new Header("Content-Type", "text/html;charset=utf-8"));
        headers.addHeader(new Header("Content-Length", String.valueOf(htmlFile.length)));
        this.responseBody = htmlFile;
    }

    public void addCookiePath(String path) {
        this.cookies.setPath(path);
    }

    public void response200CSS(byte[] cssFile) {
        this.statusCode = StatusCode.OK;
        headers.addHeader(new Header("Content-Type", "text/css;charset=utf-8"));
        headers.addHeader(new Header("Content-Length", String.valueOf(cssFile.length)));
        this.responseBody = cssFile;
    }

}
