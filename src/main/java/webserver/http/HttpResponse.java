package webserver.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HttpResponse {

    private StatusLine statusLine;

    private final Headers headers;

    private byte[] body;

    private final OutputStream outputStream;

    private boolean isCommitted;

    public HttpResponse(OutputStream outputStream) {
        this(new StatusLine(Status.OK), new Headers(), outputStream);
    }

    public HttpResponse() {
        this(null);
    }

    private HttpResponse(StatusLine statusLine,
                         Headers headers,
                         OutputStream outputStream) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.outputStream = outputStream;
    }

    public void sendRedirect(String location) {
        this.statusLine = new StatusLine(ProtocolVersion.HTTP11, Status.FOUND);
        this.headers.setHeader("Location", location);
        commit();
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

    public void commit() {
        if (isCommitted) {
            return;
        }
        try {
            writeAndFlush();
        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            isCommitted = true;
        }
    }

    public void setContentType(String contentType) {
        this.headers.setHeader("Content-Type", contentType);
    }

    private void setContentLength(String contentLength) {
        this.headers.setHeader("Content-Length", contentLength);
    }

    private void writeAndFlush() throws IOException {
        DataOutputStream dos = new DataOutputStream(this.outputStream);

        for (String message : this.getMessages()) {
            dos.writeBytes(message + "\r\n");
        }

        dos.writeBytes("\r\n");

        if (this.hasBody()) {
            dos.write(body, 0, body.length);
        }

        dos.flush();
    }

    private List<String> getMessages() {
        List<String> messages = new ArrayList<>();
        messages.add(statusLine.getMessage());
        messages.addAll(headers.getMessages());
        return messages;
    }

    private boolean hasBody() {
        return body != null;
    }

    @Override
    public String toString() {
        return statusLine + " " + headers;
    }
}
