package webserver.response;

import webserver.enums.Protocol;
import webserver.enums.StatusCode;

public class HttpResponse {
    private StatusLine statusLine;
    private HttpResponseHeader header;
    private HttpResponseBody body;

    public HttpResponse() {
        this.statusLine = new StatusLine(Protocol.HTTP_1_1, StatusCode.OK);
        this.header = HttpResponseHeader.createEmpty();
        this.body = HttpResponseBody.createEmpty();
    }

    public void ok() {
        this.statusLine = new StatusLine(this.statusLine.getProtocol(), StatusCode.OK);
    }

    public void found() {
        this.statusLine = new StatusLine(this.statusLine.getProtocol(), StatusCode.FOUND);
    }

    public void addHeader(String key, String value) {
        header.putHeader(key, value);
    }

    public String statusLine() {
        return this.statusLine.toString();
    }

    public HttpResponseHeader getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body.get();
    }

    public void setBody(byte[] body) {
        this.body = HttpResponseBody.of(body);
    }

    public StatusCode getStatus() {
        return this.statusLine.getStatusCode();
    }

}
