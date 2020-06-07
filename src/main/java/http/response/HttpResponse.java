package http.response;

import http.HttpStatus;
import http.request.HttpRequest;
import lombok.Getter;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@Getter
public class HttpResponse {
    private ResponseHeader header;
    private byte[] body;

    public HttpResponse() {
    }

    public void body(HttpStatus httpStatus, byte[] data, String contentType) {
        this.body = data;
        this.header = ResponseHeader.of(httpStatus, contentType, data.length);
    }

    public void loadFile(HttpRequest request) throws IOException, URISyntaxException {
        try {
            byte[] dataByte = FileIoUtils.loadFileFromClasspath(request.getFilePath());
            this.body = dataByte;
            this.header = ResponseHeader.of(HttpStatus.OK, request.getContentType(), dataByte.length);
        } catch (NullPointerException e) {
            notFound(request);
        }
    }

    public void redirect(String location) {
        this.body = null;
        this.header = ResponseHeader.of(HttpStatus.FOUND, "text/html", 0, location);
    }

    public void methodNotAllowed(HttpRequest request) {
        this.header = ResponseHeader.of(HttpStatus.METHOD_NOT_ALLOWED, request.getContentType(), 0);
    }

    public void setCookie(String cookie) {
        this.header.setCookie(cookie);
    }

    public String getContentType() {
        return this.header.getContentType();
    }

    public int getContentLength() {
        return this.header.getContentLength();
    }

    public int getStatusCode() {
        return this.header.getStatusCode();
    }

    public String getStatusName() {
        return this.header.getStatusName();
    }

    public Map<String, String> getCustomHeader() {
        return this.header.getCustomHeader();
    }

    private void notFound(HttpRequest request) {
        this.header = ResponseHeader.of(HttpStatus.NOT_FOUND, request.getContentType(), 0);
    }
}
