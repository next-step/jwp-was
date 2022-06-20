package webserver.http.response;

import utils.FileIoUtils;
import webserver.http.Cookie;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
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

    public void setCookie(final Cookie cookie) throws IOException {
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

    public void setBodyContentPath(final String contentPath) throws IOException, URISyntaxException {
        if (contentPath.endsWith(".html")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("templates" + contentPath);
            this.setBody(body);
            this.setContentType("text/html;charset=utf-8");

        } else if (contentPath.endsWith(".css")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + contentPath);
            this.setBody(body);
            this.setContentType("text/css;charset=utf-8");

        } else if (contentPath.endsWith(".js")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + contentPath);
            this.setBody(body);
            this.setContentType("text/javascript;charset=utf-8");

        } else if (contentPath.contains("/fonts/")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("static" + contentPath);
            this.setBody(body);
            final String extension = this.getExtensionFrom(contentPath);
            this.setContentType("font/" + extension + ";charset=utf-8");

        } else if (contentPath.equals("/favicon.ico")) {
            final byte[] body = FileIoUtils.loadFileFromClasspath("templates" + contentPath);
            this.setBody(body);
            this.setContentType("image/x-icon;charset=utf-8");

        } else {
            throw new IllegalArgumentException("Failed to response content - content path: " + contentPath);
        }
    }

    private String getExtensionFrom(final String contentPath) {
        return contentPath.substring(
                contentPath.lastIndexOf(".") + 1
        );
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
