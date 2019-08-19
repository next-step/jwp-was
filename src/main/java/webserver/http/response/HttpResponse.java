package webserver.http.response;

import utils.FileIoUtils;
import webserver.http.mapping.RequestMapping;
import webserver.http.mapping.ResourceMapping;

public class HttpResponse {
    private static final String SURFIX = "\r\n";

    private String responseHeader;
    private byte[] responseBody;

    public HttpResponse ok(String filePath, String contentType) {
        try {
            responseBody = getResponseBody(RequestMapping.getFilePath(filePath));
            responseHeader = getResponseHeader(contentType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public HttpResponse okWithBody(String contentType, String responseBody) {
        try {
            this.responseBody = responseBody.getBytes();
            responseHeader = getResponseHeader(contentType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public HttpResponse resource(String filePath) {
        try {
            responseBody = getResponseBody(ResourceMapping.getFilePath(filePath));
            responseHeader = getResponseHeader(ResourceMapping.getContentType(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public HttpResponse found(String filePath, String cookie) {
        try {
            responseBody = getResponseBody(RequestMapping.getFilePath(filePath));
            responseHeader = statusLine("302 Found")
                    + location(filePath)
                    + cookie(cookie)
                    + SURFIX;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    private String getResponseHeader(String contentType) {
        return statusLine("200 OK")
                + contentType(contentType)
                + contentLength(responseBody.length)
                + SURFIX;
    }

    private byte[] getResponseBody(String filePath) throws Exception {
        return FileIoUtils.loadFileFromClasspath(filePath);
    }

    private String statusLine(String status) {
        return "HTTP/1.1 " + status + SURFIX;
    }

    private String contentType(String contentType) {
        return "Content-Type: " + contentType + ";charset=utf-8" + SURFIX;
    }

    private String contentLength(int length) {
        return "Content-Length: " + length + SURFIX;
    }

    private String location(String redirectUri) {
        return "Location : " + redirectUri + SURFIX;
    }

    private String cookie(String cookie) {
        if (cookie == null) {
            return null;
        }

        return cookie + SURFIX;
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

