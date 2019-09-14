package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static final String SUFFIX = "\r\n";

    private String responseHeader;
    private byte[] responseBody;

    public HttpResponse forward(String contentType, byte[] responseBody) {
        this.responseBody = responseBody;
        responseHeader = statusLine(HttpStatus.OK)
                + contentType(contentType)
                + contentLength(responseBody.length)
                + SUFFIX;

        return this;
    }

    public HttpResponse found(String requestUri, byte[] responseBody, String cookie) {
        try {
            this.responseBody = responseBody;
            responseHeader = statusLine(HttpStatus.FOUND)
                    + location(requestUri)
                    + cookie(cookie)
                    + SUFFIX;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    private String statusLine(HttpStatus status) {
        return "HTTP/1.1 " + status.line() + SUFFIX;
    }

    private String contentType(String contentType) {
        return "Content-Type: " + contentType + ";charset=utf-8" + SUFFIX;
    }

    private String contentLength(int length) {
        return "Content-Length: " + length + SUFFIX;
    }

    private String location(String redirectUri) {
        return "Location : " + redirectUri + SUFFIX;
    }

    private String cookie(String cookie) {
        if (cookie == null) {
            return null;
        }

        return cookie + SUFFIX;
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

