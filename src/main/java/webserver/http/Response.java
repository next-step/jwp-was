package webserver.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Response {

    private StatusLine statusLine;

    private final Headers headers;

    private byte[] body;

    public Response() {
        this(new StatusLine(Status.OK), new Headers(), new byte[]{});
    }

    public Response(byte[] bytes) {
        this(new StatusLine(Status.OK), new Headers(), bytes);
    }

    private Response(StatusLine statusLine, Headers headers, byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
        setContentLength(String.valueOf(body.length));
    }

    public void sendRedirect(String location) {
        this.statusLine = new StatusLine(ProtocolVersion.HTTP11, Status.FOUND);
        this.headers.addHeader("Location", location);
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public List<String> getMessages() {
        List<String> messages = new ArrayList<>();
        messages.add(statusLine.getMessage());
        messages.addAll(headers.getMessages());
        return messages;
    }

    public void setBody(byte[] body) {
        this.body = body;
        setContentLength(String.valueOf(body.length));
    }

    public void addCookie(Cookie cookie) {
        this.headers.addHeader("Set-Cookie", cookie.getValues());
    }

    public Headers getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public boolean hasBody() {
        return body != null;
    }

    public void setContentType(String contentType) {
        this.headers.addHeader("Content-Type", contentType);
    }

    private void setContentLength(String contentLength) {
        this.headers.addHeader("Content-Length", contentLength);
    }

    @Override
    public String toString() {
        return statusLine + " " + headers + " " + body.length;
    }
}
