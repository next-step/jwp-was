package model;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import service.RequestService;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClientResponse {
    private HttpStatus responseHttpStatusCode;
    private HttpHeaders responseHeaders;
    private Object body;
    private byte[] bytesBody;

    public ClientResponse(HttpStatus responseHttpStatusCode) {
        this.responseHttpStatusCode = responseHttpStatusCode;
    }

    public ClientResponse(HttpStatus responseHttpStatusCode, HttpHeaders responseHeaders) {
        this.responseHttpStatusCode = responseHttpStatusCode;
        this.responseHeaders = responseHeaders;
    }

    public ClientResponse(HttpStatus responseHttpStatusCode, HttpHeaders responseHeaders, Object body) {
        this.responseHttpStatusCode = responseHttpStatusCode;
        this.responseHeaders = responseHeaders;
        this.body = body;
    }

    public HttpStatus getResponseHttpStatusCode() {
        return responseHttpStatusCode;
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public Object getBody() {
        return body;
    }

    public byte[] getBytesBody() {
        return bytesBody;
    }

    public void setFileBody(UrlPath urlPath) throws IOException, URISyntaxException {
        this.bytesBody = FileIoUtils.loadFileFromClasspath(urlPath.getPath());
    }

    public void convertBodyToBytes() throws IOException {
        this.bytesBody = RequestService.bodyToBytes(this.body);
    }

}
