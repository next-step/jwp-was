package webserver.response;

import javax.servlet.http.HttpSession;
import webserver.common.HttpCookie;
import webserver.common.SessionManager;

public class Response {
    private final ResponseHeader responseHeader;
    private final ResponseBody responseBody;

    public Response(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
        this.responseBody = new ResponseBody(new byte[0]);
    }

    public Response(ResponseHeader responseHeader, ResponseBody responseBody) {
        this.responseBody = responseBody;
        this.responseHeader = responseHeader
                .setContentLength(responseBody.getContentLength());
    }

    public HttpSession getSession() {
        return SessionManager.getSession(getCookie());
    }

    public HttpCookie getCookie() {
        return responseHeader.getCookie();
    }

    public Response setCookie(HttpCookie cookie) {
        return new Response(responseHeader.setCookie(cookie), responseBody);
    }

    public byte[] toBytes() {
        byte[] headerBytes = responseHeader.toBytes();
        byte[] bodyBytes = responseBody.toBytes();
        byte[] responseBytes = new byte[headerBytes.length + bodyBytes.length];
        System.arraycopy(headerBytes, 0, responseBytes, 0, headerBytes.length);
        System.arraycopy(bodyBytes, 0, responseBytes, headerBytes.length, bodyBytes.length);
        return responseBytes;
    }

    @Override
    public String toString() {
        return new String(toBytes());
    }
}
