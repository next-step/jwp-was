package webserver.http.response;

import webserver.http.HttpCookie;
import webserver.http.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse implements Response {

    private static final String HTTP_PROTOCOL = "HTTP/1.1 ";
    private static final String NEW_LINE = "\r\n";

    private DataOutputStream dos;
    private HttpCookie httpCookies;
    private HttpStatus httpStatus;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.httpCookies = new HttpCookie();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public void writeHeader(HttpStatus status, String contentType, int contentLength) throws IOException {
        httpStatus = status;
        dos.writeBytes(toHttpStatusResponseHeader());
        writeCookies();
        dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8" + NEW_LINE);
        dos.writeBytes("Content-Length: " + contentLength + NEW_LINE);
        dos.writeBytes(NEW_LINE);
    }

    @Override
    public void redirect(String location) throws IOException {
        httpStatus = HttpStatus.REDIRECT;
        dos.writeBytes(toHttpStatusResponseHeader());
        writeCookies();
        dos.writeBytes("Location: " + location + NEW_LINE);
        dos.writeBytes(NEW_LINE);
    }

    private void writeCookies() throws IOException {
        if(httpCookies.isEmpty()) {
            return;
        }

        dos.writeBytes(httpCookies.toString());
    }

    @Override
    public void writeBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    @Override
    public void error(HttpStatus status) throws IOException {
        httpStatus = status;
        dos.writeBytes(toHttpStatusResponseHeader());
    }


    public void addCookie(String key, String value) {
        httpCookies.add(key, value);
    }

    private String toHttpStatusResponseHeader() {
        return HTTP_PROTOCOL + httpStatus.getStatusCode()+ " " + httpStatus.getAction() + NEW_LINE;
    }
}
