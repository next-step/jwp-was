package webserver.response;

import webserver.HttpHeaders;
import webserver.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static webserver.HttpHeaders.TEXT_HTML_CHARSET_UTF_8;

public class HttpResponse implements Response {

    private static final String CRLF = "\r\n";

    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders = new HttpHeaders();
    private byte[] responseBody = {};

    public static Response ok(byte[] responseBody) {
        return ok(responseBody, TEXT_HTML_CHARSET_UTF_8);
    }

    public static Response ok(byte[] responseBody, String contentType) {
        HttpResponse response = new HttpResponse();
        response.httpStatus = HttpStatus.SUCCESS;
        response.responseBody = responseBody;
        response.setResponseBody(contentType);
        return response;
    }

    private void setResponseBody(String contentType) {
        httpHeaders.setContentType(contentType);
        httpHeaders.setContentLength(this.responseBody.length);
    }

    public static Response notFound() {
        HttpResponse response = new HttpResponse();
        response.httpStatus = HttpStatus.NOT_FOUND;
        return response;
    }

    public static Response internalServerError() {
        HttpResponse response = new HttpResponse();
        response.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return response;
    }

    public static Response redirect(String location) {
        HttpResponse response = new HttpResponse();
        response.httpStatus = HttpStatus.REDIRECT;
        response.setLocation(location);
        return response;
    }

    private void setLocation(String location) {
        this.httpHeaders.setLocation(location);
    }

    public void setCookie(String value) {
        httpHeaders.setCookie(value);
    }

    @Override
    public String getHeader(HeaderProperty key) {
        return httpHeaders.get(key);
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

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

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpStatus=" + httpStatus +
                ", httpHeaders=" + httpHeaders +
                ", responseBody=" + Arrays.toString(responseBody) +
                '}';
    }
}
