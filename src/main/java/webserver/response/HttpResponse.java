package webserver.response;

import java.nio.charset.StandardCharsets;
import webserver.domain.ContentType;
import webserver.enums.Protocol;
import webserver.enums.StatusCode;
import webserver.domain.HttpHeader;

public class HttpResponse {
    private StatusLine statusLine;
    private HttpResponseHeader header;
    private HttpResponseBody body;

    public HttpResponse() {
        this(new StatusLine(Protocol.HTTP_1_1, StatusCode.OK), HttpResponseHeader.createEmpty(), HttpResponseBody.createEmpty());
    }

    public HttpResponse(StatusLine statusLine) {
        this(statusLine, HttpResponseHeader.createEmpty(), HttpResponseBody.createEmpty());
    }

    private HttpResponse(StatusLine statusLine, HttpResponseHeader header, HttpResponseBody body) {
        this.statusLine = statusLine;
        this.header = header;
        this.body = body;
    }

    public static HttpResponse createBadRequest() {
        return new HttpResponse(new StatusLine(Protocol.HTTP_1_1, StatusCode.BAD_REQUEST));
    }

    public void ok() {
        this.statusLine = new StatusLine(this.statusLine.getProtocol(), StatusCode.OK);
        header.putHeader(HttpHeader.CONTENT_TYPE, ContentType.HTML.type());
    }

    public void okWithBody(byte[] body, String contentType) {
        this.statusLine = new StatusLine(this.statusLine.getProtocol(), StatusCode.OK);
        this.body = HttpResponseBody.of(body);
        header.putHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(this.body.length()));
        header.putHeader(HttpHeader.CONTENT_TYPE, contentType);
    }

    public void found(String redirection) {
        this.statusLine = new StatusLine(this.statusLine.getProtocol(), StatusCode.FOUND);
        header.putHeader(HttpHeader.LOCATION, redirection);
        header.putHeader(HttpHeader.CONTENT_TYPE, ContentType.HTML.type());
    }

    public void badRequest() {
        this.statusLine = new StatusLine(this.statusLine.getProtocol(), StatusCode.BAD_REQUEST);
    }

    public void notFound() {
        this.statusLine = new StatusLine(this.statusLine.getProtocol(), StatusCode.NOT_FOUND);
        String notFoundGoogle = "404. That’s an error.\n"
            + "The requested URL was not found on this server. That’s all we know.";
        this.body = HttpResponseBody.of(notFoundGoogle.getBytes(StandardCharsets.UTF_8));
        header.putHeader(HttpHeader.CONTENT_LENGTH, String.valueOf(this.body.length()));

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
