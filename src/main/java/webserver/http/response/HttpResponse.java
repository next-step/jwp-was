package webserver.http.response;

import webserver.http.Header;
import webserver.http.request.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpResponse implements Response {

    private StatusLine statusLine;

    private byte[] body;

    private Header header = new Header(new HashMap<>(), null);

    public HttpResponse(HttpRequest httpRequest) {
        statusLine = new StatusLine(httpRequest.getProtocol(), httpRequest.getVersion());
    }

    @Override
    public int getLength() {
        return body == null ? 0 : body.length;
    }

    @Override
    public byte[] getBody() {
        return body;
    }

    @Override
    public int getStatus() {
        return statusLine.getHttpStatus().status;
    }

    @Override
    public String getStatusMessage() {
        return statusLine.getHttpStatus().getStatusMessage();
    }

    @Override
    public String getLocation() {
        return header.get("Location");
    }

    public void ok(byte[] body) {
        statusLine.ok();

        this.body = body;
        header.put("Content-Type", "text/html;charset=utf-8");
        header.put("Content-Length", String.valueOf(getLength()));
    }

    public void okWithContentType(byte[] body, String contentType) {
        statusLine.ok();

        this.body = body;
        header.put("Content-Type", contentType);
        header.put("Content-Length", String.valueOf(getLength()));
    }

    public void redirect(String location) {
        statusLine.redirect();

        header.put("Location", location);
        header.put("Content-Type", "text/html;charset=utf-8");
        header.put("Content-Length", String.valueOf(getLength()));
    }

    public List<String> toResponseHeader() {
        List<String> responseHeader = new ArrayList();
        responseHeader.add(statusLine.toString());
        responseHeader.addAll(header.toHeaderStrings());
        return responseHeader;
    }

    public void setCookie(String cookie) {
        header.put("Set-Cookie", cookie);
    }
}
