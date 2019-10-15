package webserver.http.response;

import utils.HttpStringUtils;

public class HttpResponse {
    private static final String SUFFIX = "\r\n";

    private String responseHeader;
    private byte[] responseBody;

    public HttpResponse forward(String contentType, byte[] responseBody) {
        this.responseBody = responseBody;
        this.responseHeader = HttpStringUtils.concat(statusLine(HttpStatus.OK)
                , contentType(contentType)
                , contentLength(responseBody.length)
                , SUFFIX);

        return this;
    }

    public HttpResponse found(String requestUri, byte[] responseBody) {
        this.responseBody = responseBody;
        this.responseHeader = HttpStringUtils.concat(statusLine(HttpStatus.FOUND)
                , location(requestUri)
                , SUFFIX);

        return this;
    }

    public HttpResponse found(String requestUri, byte[] responseBody, String[] cookies) {
        this.responseBody = responseBody;
        this.responseHeader = HttpStringUtils.concat(statusLine(HttpStatus.FOUND)
                , location(requestUri)
                , cookies(cookies)
                , SUFFIX);

        return this;
    }

    private String statusLine(HttpStatus status) {
        return HttpStringUtils.concat("HTTP/1.1 ", status.line(), SUFFIX);
    }

    private String contentType(String contentType) {
        return HttpStringUtils.concat("Content-Type: ", contentType, ";charset=utf-8", SUFFIX);
    }

    private String contentLength(int length) {
        return HttpStringUtils.concat("Content-Length: ", Integer.toString(length), SUFFIX);
    }

    private String location(String redirectUri) {
        return HttpStringUtils.concat("Location : ", redirectUri, SUFFIX);
    }

    private String cookies(String[] cookies) {
        StringBuilder sb = new StringBuilder();
        for (String cookie : cookies) {
            sb.append(HttpStringUtils.concat("Set-Cookie: ", cookie, SUFFIX));
        }

        return sb.toString();
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public int getResponseBodyLength() {
        return responseBody.length;
    }
}