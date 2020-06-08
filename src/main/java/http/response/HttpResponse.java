package http.response;

import http.common.Cookies;
import http.common.HeaderField;
import http.common.HeaderFieldName;

public class HttpResponse {

    private StatusCode statusCode;
    private final Header header;
    private final Cookies cookies;
    private byte[] responseBody;

    public HttpResponse() {
        header = new Header();
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
        HeaderField locationHeader = new HeaderField(HeaderFieldName.LOCATION, locationUrl);
        header.addHeader(locationHeader);
    }

    public Header getHeader() {
        return header;
    }

    public String getHeader(String headerName) {
        return header.getValue(headerName);
    }

    public byte[] getBody() {
        return responseBody;
    }

    public void response200HTML(byte[] htmlFile) {
        this.statusCode = StatusCode.OK;
        header.addHeader(new HeaderField(HeaderFieldName.CONTENT_TYPE, "text/html;charset=utf-8"));
        header.addHeader(new HeaderField(HeaderFieldName.CONTENT_LENGTH, String.valueOf(htmlFile.length)));
        this.responseBody = htmlFile;
    }

    public void addCookiePath(String path) {
        this.cookies.setPath(path);
    }

    public void response200CSS(byte[] cssFile) {
        this.statusCode = StatusCode.OK;
        header.addHeader(new HeaderField(HeaderFieldName.CONTENT_TYPE, "text/css;charset=utf-8"));
        header.addHeader(new HeaderField(HeaderFieldName.CONTENT_LENGTH, String.valueOf(cssFile.length)));
        this.responseBody = cssFile;
    }

}
