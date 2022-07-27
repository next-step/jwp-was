package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import http.Cookie;
import http.HttpStatus;
import http.request.ContentType;
import http.request.Protocol;
import utils.FileIoUtils;

public class HttpResponse {

    private final Protocol protocol;
    private final HttpStatus httpStatus;
    private final Map<String, String> headers;
    private final String body;
    private final Set<Cookie> cookies;

    public HttpResponse(Protocol protocol, HttpStatus httpStatus, Map<String, String> headers, String body,
        Set<Cookie> cookies) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.headers = headers;
        this.body = body;
        this.cookies = cookies;
    }

    public HttpResponse(HttpStatus status, Map<String, String> headers) {
        this(Protocol.of("HTTP/1.1"), status, headers, "", Set.of());
    }

    public HttpResponse(HttpStatus status, Map<String, String> headers, Cookie cookie) {
        this(Protocol.of("HTTP/1.1"), status, headers, "", Set.of(cookie));
    }

    public static HttpResponse found(String location) {
        return new HttpResponse(HttpStatus.FOUND, Map.of("Location", location));
    }

    public static HttpResponse ok(String body) {
        return new HttpResponse(Protocol.of("HTTP/1.1"), HttpStatus.OK, Map.of(), body, Set.of());
    }

    public static HttpResponse parseStaticFile(String url, String fileExtension) {
        var bytes = FileIoUtils.loadFileFromClasspath(url);
        var contentType = ContentType.of(fileExtension);

        return new HttpResponse(
            Protocol.of("HTTP/1.1"),
            HttpStatus.OK,
            Map.of("Content-Type", contentType.getMessage()),
            new String(bytes, StandardCharsets.UTF_8),
            Set.of()
        );
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public Set<Cookie> getCookies() {
        return cookies;
    }

    public void write(DataOutputStream dataOutputStream) {
        writeHeader(dataOutputStream);
        writeBody(dataOutputStream);
    }

    private void writeHeader(DataOutputStream dos) {
        var protocol = getProtocol();
        var httpStatus = getHttpStatus();
        try {
            dos.writeBytes(String.format("%s/%s %d %s \r\n",
                protocol.getProtocolType(),
                protocol.getVersion(),
                httpStatus.getStatusCode(),
                httpStatus.getMessage())
            );

            if (getHeaders().get("Content-Type") == null) {
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            }
            if (getHeaders().get("Content-Length") == null) {
                dos.writeBytes("Content-Length: " + getBody().length() + "\r\n");
            }

            for (Map.Entry<String, String> entry : getHeaders().entrySet()) {
                dos.writeBytes(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
            }

            for (Cookie cookie : getCookies()) {
                var prefix = String.format("Set-Cookie: %s=%s", cookie.getKey(), cookie.getValue());

                var cookieResponse = Stream.concat(Stream.of(prefix), cookie.getOptions().stream())
                    .collect(Collectors.joining("; "));

                dos.writeBytes(cookieResponse + "\r\n");
            }

            dos.writeBytes("\r\n");
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private void writeBody(DataOutputStream dos) {
        var body = getBody();
        try {
            dos.write(body.getBytes(StandardCharsets.UTF_8), 0, body.length());
            dos.flush();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
