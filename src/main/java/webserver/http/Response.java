package webserver.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Response {

    private StatusLine statusLine;

    private Headers headers;

    private byte[] body;

    public Response(StatusLine statusLine, Headers headers) {
        this(statusLine, headers, new byte[]{});
    }

    public Response(StatusLine statusLine, Headers headers, byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
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

    public Headers getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public boolean hasBody() {
        return body != null;
    }

    @Override
    public String toString() {
        return statusLine + " " + headers + " " + body.length;
    }
}
