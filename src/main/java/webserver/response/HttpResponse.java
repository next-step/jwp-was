package webserver.response;

import webserver.HttpHeaders;
import webserver.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static webserver.HttpHeaders.TEXT_HTML_CHARSET_UTF_8;

public class HttpResponse implements Response {

    private static final String CRLF = "\r\n";

    private OutputStream out;
    private HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    private HttpHeaders httpHeaders = new HttpHeaders();
    private byte[] responseBody = {};

    public HttpResponse(OutputStream out) {
        this.out = out;
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
    public void ok(byte[] responseBody) throws IOException {
        ok(responseBody, TEXT_HTML_CHARSET_UTF_8);
    }

    @Override
    public void ok(byte[] responseBody, String contentType) throws IOException {
        this.responseBody = responseBody;
        httpStatus = HttpStatus.SUCCESS;
        setContentLength(this.responseBody.length);
        setContentType(contentType);
        send();
    }

    @Override
    public void notFound() throws IOException {
        send();
    }

    @Override
    public void internalServerError() throws IOException {
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        send();
    }

    @Override
    public void redirect(String location) throws IOException {
        httpStatus = HttpStatus.REDIRECT;
        setLocation(location);
        send();
    }

    private void send() throws IOException {
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
