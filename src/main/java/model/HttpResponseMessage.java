package model;

import types.HttpStatus;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class HttpResponseMessage {
    private final HttpStatus responseHttpStatus;
    private HttpHeaders responseHeaders;
    private byte[] bytesBody;

    public HttpResponseMessage(HttpStatus responseHttpStatus) {
        this.responseHttpStatus = responseHttpStatus;
    }

    public HttpResponseMessage(HttpStatus responseHttpStatus, HttpHeaders responseHeaders) {
        this.responseHttpStatus = responseHttpStatus;
        this.responseHeaders = responseHeaders;
    }

    public HttpResponseMessage(HttpStatus responseHttpStatus, HttpHeaders responseHeaders, Object body) throws IOException {
        this.responseHttpStatus = responseHttpStatus;
        this.responseHeaders = responseHeaders;
        this.bytesBody = IOUtils.bodyToBytes(body);
    }

    public HttpStatus getResponseHttpStatus() {
        return responseHttpStatus;
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public byte[] getBytesBody() {
        return bytesBody;
    }

    public void setFileBody(UrlPath urlPath, boolean isRequestForTemplate) throws IOException, URISyntaxException {
        this.bytesBody = FileIoUtils.loadFileFromClasspath(urlPath.getPath(), isRequestForTemplate);
    }

    public void sendResponse(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        this.makeResponseHeader(dataOutputStream);
        this.makeResponseBody(dataOutputStream);
    }

    private void makeResponseHeader(DataOutputStream dataOutputStream) throws IOException {
        byte[] body = this.bytesBody;

        HttpStatus responseHttpStatus = this.getResponseHttpStatus();
        HttpHeaders responseHeaders = this.getResponseHeaders();

        dataOutputStream.writeBytes(String.format("HTTP/1.1 %s %s\r\n", responseHttpStatus.getCode(), responseHttpStatus.name()));
        if (body != null) {
            dataOutputStream.writeBytes("Content-Length: " + body.length + "\r\n");
        }

        if (responseHeaders != null) {
            responseHeaders.getHeaders().keySet()
                    .forEach(key -> {
                        try {
                            String headerValue = responseHeaders.getHeaders().get(key);
                            dataOutputStream.writeBytes(key + ": " + headerValue + "\r\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
        dataOutputStream.writeBytes("\r\n");
    }

    private void makeResponseBody(DataOutputStream dataOutputStream) throws IOException {
        byte[] body = this.bytesBody;
        if (body == null) {
            dataOutputStream.flush();
            return;
        }

        dataOutputStream.write(body, 0, body.length);
        dataOutputStream.flush();
    }

}
