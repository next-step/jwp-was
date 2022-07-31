package model;

import service.RequestService;
import types.HttpStatus;
import utils.FileIoUtils;
import model.HttpHeaders;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClientResponse {
    private final HttpStatus responseHttpStatus;
    private HttpHeaders responseHeaders;
    private Object body;
    private byte[] bytesBody;

    public ClientResponse(HttpStatus responseHttpStatus) {
        this.responseHttpStatus = responseHttpStatus;
    }

    public ClientResponse(HttpStatus responseHttpStatus, HttpHeaders responseHeaders) {
        this.responseHttpStatus = responseHttpStatus;
        this.responseHeaders = responseHeaders;
    }

    public ClientResponse(HttpStatus responseHttpStatus, HttpHeaders responseHeaders, Object body) {
        this.responseHttpStatus = responseHttpStatus;
        this.responseHeaders = responseHeaders;
        this.body = body;
    }

    public HttpStatus getResponseHttpStatus() {
        return responseHttpStatus;
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

    public void setFileBody(UrlPath urlPath, boolean isRequestForTemplate) throws IOException, URISyntaxException {
        this.bytesBody = FileIoUtils.loadFileFromClasspath(urlPath.getPath(), isRequestForTemplate);
    }

    public void convertBodyToBytes() throws IOException {
        this.bytesBody = RequestService.bodyToBytes(this.body);
    }

}
