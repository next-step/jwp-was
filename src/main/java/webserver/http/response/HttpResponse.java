package webserver.http.response;

import webserver.http.Cookie;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpResponse {
    private final ResponseHeaders headers = new ResponseHeaders();
    private byte[] body = null;

    private final DataOutputStream out;

    public HttpResponse(final OutputStream out) {
        this.out = new DataOutputStream(out);
    }

    public void setContentType(final String contentType) {
        this.headers.add("Content-Type", contentType);
    }

    public void setCookie(final Cookie cookie) {
        final String setCookieHeaderValue =
                cookie.getName() + "=" + cookie.getValue() + "; " +
                        "Path" + "=" + cookie.getPath();

        this.headers.add("Set-Cookie", setCookieHeaderValue);
    }

    public void setBody(final String body) {
        this.setBody(body.getBytes(StandardCharsets.UTF_8));
    }

    public void setBody(final byte[] body) {
        this.body = body;
        this.headers.add("Content-Length", Integer.toString(this.body.length));
    }

    public void responseOK() throws IOException {
        this.out.writeBytes("HTTP/1.1 200 OK \r\n");
        this.writeResponseHeaders();
        this.writeBody();
        this.out.flush();
    }

    public void responseRedirect(final String location) throws IOException {
        this.out.writeBytes("HTTP/1.1 302 Found \r\n");
        this.out.writeBytes("Location: " + location + "\r\n");
        this.writeResponseHeaders();
        this.out.flush();
    }

    public void responseNotFound() throws IOException {
        this.out.writeBytes("HTTP/1.1 404 Not Found \r\n");
        this.writeResponseHeaders();
        this.out.flush();
    }

    public void responseMethodNotAllowed() throws IOException {
        this.out.writeBytes("HTTP/1.1 405 Method Not Allowed \r\n");
        this.writeResponseHeaders();
        this.out.flush();
    }

    private void writeResponseHeaders() throws IOException {
        final Map<String, String> headers = this.headers.getAll();

        if (!headers.isEmpty()) {
            for (final Map.Entry<String, String> header : headers.entrySet()) {
                final String headerName = header.getKey();
                final String headerValue = header.getValue();
                this.out.writeBytes(headerName + ": " + headerValue + "\r\n");
            }
            this.out.writeBytes("\r\n");
        }
    }

    private void writeBody() throws IOException {
        this.out.write(this.body, 0, this.body.length);
    }
}
