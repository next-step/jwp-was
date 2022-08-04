package model.response;

import model.HttpHeader;

import java.util.List;

public class HttpResponseMessage {
    private final ResponseLine responseLine;
    private final HttpHeader httpHeader;
    private final byte[] body;

    public HttpResponseMessage(ResponseLine responseLine, HttpHeader httpHeader, byte[] body) {
        this.responseLine = responseLine;
        this.httpHeader = httpHeader;
        this.body = body;
    }

    public String getResponseLine() {
        return responseLine.getResponseLine();
    }

    public List<String> getHttpHeaders() {
        return httpHeader.getHttpHeaders();
    }

    public byte[] getBody() {
        return body;
    }
}
