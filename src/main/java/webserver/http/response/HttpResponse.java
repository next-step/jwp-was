package webserver.http.response;

import webserver.http.Header;
import webserver.http.request.HttpRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class HttpResponse implements Response {

    private ResponseLine responseLine;

    private byte[] body;

    private Header header = new Header(new HashMap<>());

    public HttpResponse(HttpRequest httpRequest) {
        responseLine = new ResponseLine(httpRequest.getProtocol(), httpRequest.getVersion());
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
        return responseLine.getHttpStatus().status;
    }

    @Override
    public String getStatusMessage() {
        return responseLine.getHttpStatus().getStatusMessage();
    }

    @Override
    public String getLocation() {
        return header.get("location");
    }

    public void ok(byte[] body) {
        responseLine.ok();
        this.body = body;
        header.put("Content-Type", "text/html;charset=utf-8");
        header.put("Content-Length", String.valueOf(getLength()));
    }

    public void redirect(String location) {
        responseLine.redirect();
        header.put("Location", location);
        header.put("Content-Type", "text/html;charset=utf-8");
        header.put("Content-Length", String.valueOf(getLength()));
    }

    public List<String> toResponseHeader() {
        List<String> responseHeader = new ArrayList();
        responseHeader.add(responseLine.toString());
        responseHeader.addAll(header.toHeaderStrings());
        return responseHeader;

    }
}
