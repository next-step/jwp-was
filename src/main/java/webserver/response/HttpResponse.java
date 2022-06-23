package webserver.response;

import webserver.request.Headers;
import webserver.request.Protocol;

/**
 * 현재 DTO느낌으로 사용하고 있으나 오브젝트로 역할을 가져가야한다.
 */
public class HttpResponse {

    private final byte[] body;
    private final String code; // TODO enum으로 변경
    private final Headers headers;
    private final Protocol protocol;

    public HttpResponse(byte[] body, String code, Headers headers, Protocol protocol) {
        this.body = body;
        this.code = code;
        this.headers = headers;
        this.protocol = protocol;
    }

    public HttpResponse(byte[] body, String code) {
        this.body = body;
        this.code = code;
        this.headers = Headers.empty();
        this.protocol = Protocol.defaultProtocol();
    }


    public byte[] getBody() {
        return body;
    }

    public String getCode() {
        return code;
    }

    public String getCookieStr() {
        return headers.get("Cookie");
    }

    public Headers getHeaders() {
        return headers;
    }

    public void putCookie(String key, String value) {
        headers.putCookie(key, value);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setTextHtml() {
        headers.setTextHtml();
    }

    public void setTextCss() {
        headers.setTextCss();
    }

    public String response() {
        String result = String.format("%s %s OK \r\n", protocol.response(), code);
        headers.put(Headers.CONTENT_LENGTH, String.valueOf(body.length));
        return result + headers.response();
    }
}
