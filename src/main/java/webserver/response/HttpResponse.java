package webserver.response;

import webserver.enums.Protocol;
import webserver.enums.StatusCode;
import webserver.request.HttpHeader;

public class HttpResponse {

    private Protocol protocol;
    private StatusCode statusCode;
    private HttpHeader httpHeader;
    private byte[] body;

    public HttpResponse() {
        this.protocol = Protocol.HTTP_1_1;
        this.statusCode = StatusCode.OK;
        this.httpHeader = new HttpHeader();
        this.body = new byte[0];
    }

    public void protocol1_1() {
        this.protocol = Protocol.HTTP_1_1;
    }

    public void statusOk() {
        this.statusCode = StatusCode.OK;
    }

    public void statusBadRequest() {
        this.statusCode = StatusCode.BAD_REQUEST;
    }

    public void addHeader(String key, String value) {
        httpHeader.putHeader(key, value);
    }

    public String responseLine() {
        return protocol.protocol() + "/" + protocol.version() + " " + statusCode.getStatusCode() + " " + statusCode.getMessage()
            + " \r\n";
    }

    public HttpHeader getHeader() {
        return httpHeader;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public StatusCode getStatus() {
        return this.statusCode;
    }
}
