package webserver.response;

import webserver.HttpHeaders;
import webserver.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class HttpResponse implements Response {

    private static final String CRLF = "\r\n";

    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders = new HttpHeaders();
    private byte[] responseBody = {};

    HttpResponse(HttpStatus status) {
        this.httpStatus = status;
    }

    HttpResponse(HttpStatus status, byte[] responseBody) {
        this.httpStatus = status;
        this.responseBody = responseBody;
    }


    public String getHeaderOf(String key) {
        return httpHeaders.get(key);
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    public void setCookie(String value) {
        httpHeaders.setCookie(value);
    }

    void setContentType(String contentType) {
        httpHeaders.setContentType(contentType);
    }

    void setLocation(String location) {
        httpHeaders.setLocation(location);
    }

    void setContentLength(int contentLength) {
        httpHeaders.setContentLength(contentLength);
    }

    @Override
    public String getHeader(HeaderProperty headerName) {
        return httpHeaders.get(headerName.getHeaderName());
    }

    @Override
    public String getHeader(String headerName) {
        return httpHeaders.get(headerName);
    }

    @Override
    public void addHeader(HeaderProperty key, String value) {

    }

    @Override
    public void send(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(httpStatus.getStatusLine().concat(CRLF));
        writeHeaders(dos);
        dos.writeBytes(CRLF);
        writeReposeBody(dos);
    }

    private void writeHeaders(DataOutputStream dos) throws IOException {
        List<String> output = httpHeaders.output();
        String headers = String.join(CRLF, output);
        dos.writeBytes(headers.concat(CRLF));
    }

    private void writeReposeBody(DataOutputStream dos) throws IOException {
        dos.write(responseBody, 0, responseBody.length);
        dos.flush();
    }
}
