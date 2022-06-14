package webserver.response;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

// Add VOs
public class HttpResponse {
    private final DataOutputStream out;

    public HttpResponse(final OutputStream out) {
        this.out = new DataOutputStream(out);
    }

    private final Map<String, String> headers = new HashMap<>();
    private Cookie cookie = null;
    private byte[] body = null;

    public void setCookie(final Cookie cookie) throws IOException {
        this.cookie = cookie;
    }

    public void setBodyContentPath(final String contentPath) throws IOException, URISyntaxException {
        if (contentPath.endsWith(".html")) {
            this.body = FileIoUtils.loadFileFromClasspath("templates/" + contentPath);
            this.headers.put("Content-Type", "text/html;charset=utf-8");

        } else if (contentPath.endsWith(".css")) {
            this.body = FileIoUtils.loadFileFromClasspath("static/" + contentPath);
            this.headers.put("Content-Type", "text/css;charset=utf-8");

        } else if (contentPath.endsWith(".js")) {
            this.body = FileIoUtils.loadFileFromClasspath("static/" + contentPath);
            this.headers.put("Content-Type", "text/javascript;charset=utf-8");

        } else {
            throw new IllegalArgumentException("Failed to response content - content path: " + contentPath);
        }


        this.headers.put("Content-Length", Integer.toString(this.body.length));
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

    private void writeResponseHeaders() throws IOException {
        if (this.cookie != null) {
            final String setCookieHeader = new StringBuilder()
                    .append("Set-Cookie: ")
                    .append(this.cookie.getName()).append("=").append(this.cookie.getValue()).append("; ")
                    .append("Path").append("=").append(this.cookie.getPath())
                    .append("\r\n")
                    .toString();
            this.out.writeBytes(setCookieHeader);
        }

        if (!this.headers.isEmpty()) {
            for (final Map.Entry<String, String> header : this.headers.entrySet()) {
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
