package webserver.http.response;

import webserver.http.Cookie;
import webserver.http.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpResponse implements Response {

    private static final String COOKIE_PREFIX = "Set-Cookie: ";
    private static final String HTTP_PROTOCOL = "HTTP/1.1 ";
    private static final String NEW_LINE = "\r\n";

    private DataOutputStream dos;
    private List<Cookie> cookies;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.cookies = new ArrayList<>();
    }

    @Override
    public void writeHeader(HttpStatus status, String contentType, int contentLength) throws IOException {
        dos.writeBytes(toHttpStatusResponseHeader(status));
        writeCookies();
        dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8" + NEW_LINE);
        dos.writeBytes("Content-Length: " + contentLength + NEW_LINE);
        dos.writeBytes(NEW_LINE);
    }

    @Override
    public void redirect(String location) throws IOException {
        dos.writeBytes(toHttpStatusResponseHeader(HttpStatus.REDIRECT));
        writeCookies();
        dos.writeBytes("Location: " + location + NEW_LINE);
        dos.writeBytes(NEW_LINE);
    }

    private void writeCookies() throws IOException {
        if(cookies.isEmpty()) {
            return;
        }

        String cookiesStr = cookies.stream()
                .map(Cookie::toString)
                .collect(Collectors.joining(NEW_LINE, COOKIE_PREFIX, NEW_LINE));
        dos.writeBytes(cookiesStr);
    }

    @Override
    public void writeBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }


    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public Cookie getCookie(String key) {
        return cookies.stream()
                .filter(cookie -> cookie.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void error(HttpStatus status) throws IOException {
        dos.writeBytes(toHttpStatusResponseHeader(status));
    }

    private String toHttpStatusResponseHeader(HttpStatus status) {
        return HTTP_PROTOCOL + status.getStatusCode()+ " " + status.getAction() + NEW_LINE;
    }
}
