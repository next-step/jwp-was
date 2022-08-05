package webserver.http.response;

import webserver.http.Header;
import webserver.http.request.requestline.Protocol;
import webserver.http.request.requestline.ProtocolType;
import webserver.http.request.requestline.Version;
import webserver.http.response.statusline.StatusCode;
import webserver.http.response.statusline.StatusLine;

import java.util.Map;

public class HttpResponse {

    private StatusLine statusLine;
    private Header header;
    private byte[] body;

    public HttpResponse(StatusLine statusLine, Header header, byte[] body) {
        validate(statusLine, header);
        this.statusLine = statusLine;
        this.header = header.add("Content-Length", String.valueOf(body.length));
        this.body = body;
    }

    public static HttpResponse of(StatusLine statusLine, Header header, byte[] body) {
        return new HttpResponse(statusLine, header, body);
    }

    public static HttpResponse of(StatusLine statusLine, Header header) {
        return new HttpResponse(statusLine, header, new byte[0]);
    }

    public static HttpResponse ok(Header header, byte[] body) {
        return new HttpResponse(StatusLine.of(new Protocol(ProtocolType.HTTP, Version.ONE_ONE), StatusCode.OK), header, body);
    }

    public static HttpResponse notFound() {
        return new HttpResponse(StatusLine.of(new Protocol(ProtocolType.HTTP, Version.ONE_ONE), StatusCode.NOT_FOUND), new Header(Map.of()), new byte[0]);
    }

    public static HttpResponse redirect(String path) {
        return new HttpResponse(StatusLine.of(new Protocol(ProtocolType.HTTP, Version.ONE_ONE), StatusCode.FOUND), new Header(Map.of("Location", path)), new byte[0]);
    }

    public static HttpResponse redirect(String path, Header header) {
        return new HttpResponse(StatusLine.of(new Protocol(ProtocolType.HTTP, Version.ONE_ONE), StatusCode.FOUND), header.add("Location", path), new byte[0]);
    }

    public String response() {
        return this.statusLine.statusLine() + this.header.header() + "\r\n";
    }

    public byte[] getBody() {
        return this.body;
    }

    public boolean isHeaderValueEqual(String key, String value) {
        return this.header.isHeaderValueEqual(key, value);
    }
    public int getContentLength() {
        return this.body.length;
    }

    public boolean isStatusCodeEqual(StatusCode statusCode) {
        return this.statusLine.isStatusCodeEqual(statusCode);
    }

    private static void validate(StatusLine statusLine, Header header) {
        validateStatusLine(statusLine);
        validateHeader(header);
    }

    private static void validateHeader(Header header) {
        if (header == null) {
            throw new IllegalArgumentException("응답 헤더는 null 일 수 없습니다.");
        }
    }

    private static void validateStatusLine(StatusLine statusLine) {
        if (statusLine == null) {
            throw new IllegalArgumentException("응답 라인은 null 일 수 없습니다.");
        }
    }
}
