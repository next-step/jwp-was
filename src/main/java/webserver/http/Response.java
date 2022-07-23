package webserver.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Response {

    private StatusLine statusLine;

    private Headers headers;

    private byte[] body;

    public Response() {
    }

    public Response(StatusLine statusLine, Headers headers, byte[] body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public List<String> getMessages() {
        List<String> messages = new ArrayList<>();
        messages.add(statusLine.getMessage());
        messages.addAll(headers.getMessages());
        return messages;
    }

    public byte[] getBody() {
        return body;
    }

    public boolean hasBody() {
        return body != null;
    }
}
