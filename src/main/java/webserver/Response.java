package webserver;

import webserver.request.Cookie;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import static webserver.HttpHeaders.COOKIE;
import static webserver.HttpHeaders.TEXT_HTML_CHARSET_UTF_8;
import static webserver.HttpStatus.*;

public class Response {

    private static final String CRLF = "\r\n";

    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders = new HttpHeaders();
    private byte[] responseBody = {};

    private Response(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public static Response ok(String responseBody) {
        return ok(responseBody.getBytes());
    }

    public static Response ok(byte[] responseBody) {
        return ok(responseBody, TEXT_HTML_CHARSET_UTF_8);
    }

    public static Response ok(byte[] responseBody, String contentType) {
        Response response = new Response(SUCCESS);

        response.httpHeaders.setContentType(contentType);
        response.setContentLength(responseBody.length);
        response.responseBody = responseBody;

        return response;
    }

    private void setContentLength(int contentLength) {
        httpHeaders.setContentLength(contentLength);
    }

    public static Response redirect(String location) {
        Response response = new Response(REDIRECT);
        response.setLocation(location);
        return response;
    }

    private void setLocation(String location) {
        httpHeaders.setLocation(location);
    }

    static Response notFound() {
        return new Response(NOT_FOUND);
    }

    static Response internalServerError() {
        return new Response(INTERNAL_SERVER_ERROR);
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    public String getHeaderOf(String key) {
        return httpHeaders.get(key);
    }

    public void setCookie(String value) {
        httpHeaders.setCookie(value);
    }

    void send(OutputStream out) throws IOException {
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
