package webserver.http.response;

import webserver.http.header.type.EntityHeader;
import webserver.http.header.Header;
import webserver.http.header.HeaderKey;
import webserver.http.header.type.ResponseHeader;
import webserver.http.response.statusline.StatusCode;
import webserver.http.response.statusline.StatusLine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private StatusLine statusLine;
    private Header header;
    private byte[] body;

    public HttpResponse(StatusLine statusLine, Header header, byte[] body) {
        validate(statusLine, header);
        this.statusLine = statusLine;
        this.header = header.add(EntityHeader.CONTENT_LENGTH, String.valueOf(body.length));
        this.body = body;
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

    public static HttpResponse of(StatusLine statusLine, Header header, byte[] body) {
        return new HttpResponse(statusLine, header, body);
    }

    public static HttpResponse of(StatusLine statusLine, Header header) {
        return new HttpResponse(statusLine, header, new byte[0]);
    }

    public static HttpResponse ok(Header header, byte[] body) {
        return new HttpResponse(StatusLine.ofHttp_V1_1_Ok(), header, body);
    }

    public static HttpResponse notFound() {
        return new HttpResponse(StatusLine.ofHttp_V1_1_NotFound(), new Header(new HashMap<>()), new byte[0]);
    }

    public static HttpResponse redirect(String path) {
        Map<HeaderKey, String> fields = new HashMap<>();
        fields.put(ResponseHeader.LOCATION, path);
        return new HttpResponse(StatusLine.ofHttp_V1_1_Found(), new Header(fields), new byte[0]);
    }

    public static HttpResponse redirect(String path, Header header) {
        return new HttpResponse(StatusLine.ofHttp_V1_1_Found(), header.add(ResponseHeader.LOCATION, path), new byte[0]);
    }

    public String response() {
        return this.statusLine.statusLine() + this.header.header() + "\r\n";
    }

    public byte[] getBody() {
        return this.body;
    }

    public boolean isHeaderValueEqual(HeaderKey key, String value) {
        return this.header.isHeaderValueEqual(key, value);
    }
    public int getContentLength() {
        return this.body.length;
    }

    public boolean isStatusCodeEqual(StatusCode statusCode) {
        return this.statusLine.isStatusCodeEqual(statusCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpResponse that = (HttpResponse) o;

        if (!statusLine.equals(that.statusLine)) return false;
        if (!header.equals(that.header)) return false;
        return Arrays.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        int result = statusLine.hashCode();
        result = 31 * result + header.hashCode();
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }
}
