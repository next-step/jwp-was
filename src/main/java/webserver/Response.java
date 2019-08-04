package webserver;

import com.sun.net.httpserver.Headers;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import static webserver.HttpStatus.REDIRECT;
import static webserver.HttpStatus.SUCCESS;

public class Response {

    private static final String CRLF = "\r\n";

    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private byte[] responseBody;

    private Response(HttpStatus httpStatus, HttpHeaders httpHeaders, byte[] requestBody) {
        this.httpStatus = httpStatus;
        this.httpHeaders = httpHeaders;
        this.responseBody = requestBody;
    }

    static Response of(HttpStatus httpStatus) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type: text/html;charset=utf-8");

        return new Response(httpStatus, httpHeaders, new byte[]{});
    }

    static Response ok(String responseBody) {
        byte[] responseBodyBytes = responseBody.getBytes();
        Response response = of(SUCCESS);
        response.setContentLength(responseBodyBytes.length);
        response.responseBody = responseBodyBytes;
        return response;
    }

    static Response ok(byte[] responseBody) {
        Response response = of(SUCCESS);
        response.setContentLength(responseBody.length);
        response.responseBody = responseBody;
        return response;
    }

    static Response okWithHeaders(byte[] responseBody, HttpHeaders headers) {
        Response response = of(SUCCESS);
        response.httpHeaders = headers;
        response.setContentLength(responseBody.length);
        response.responseBody = responseBody;

        return response;
    }

    private void setContentLength(int contentLength) {
        httpHeaders.setContentLength(contentLength);
    }

    static Response redirect(String location) {
        Response response = of(REDIRECT);
        response.setLocation(location);
        return response;
    }

    static Response redirectWithHeaders(String location, HttpHeaders headers) {
        Response response = of(REDIRECT);
        response.httpHeaders = headers;
        response.setLocation(location);
        return response;
    }

    private void setLocation(String location) {
        httpHeaders.setLocation(location);
    }

    void send(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(httpStatus.output().concat(CRLF));
        writeHeaders(dos);
        dos.writeBytes(CRLF);
        writeReposeBody(dos);
    }

    private void writeHeaders(DataOutputStream dos) throws IOException {
        List<String> output = httpHeaders.output();
        String headers = output.stream()
                .collect(Collectors.joining(CRLF));
        dos.writeBytes(headers.concat(CRLF));
    }

    private void writeReposeBody(DataOutputStream dos) throws IOException {
        dos.write(responseBody, 0, responseBody.length);
        dos.flush();
    }
}
