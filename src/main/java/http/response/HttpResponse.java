package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import http.Cookie;
import http.Cookies;
import http.HttpStatus;
import http.exception.HttpResponseWriteException;
import http.request.ContentType;
import http.request.Protocol;
import http.request.session.DefaultHttpSession;
import http.request.session.HttpSession;
import utils.FileIoUtils;

public class HttpResponse {

    private final Protocol protocol;
    private final HttpStatus httpStatus;
    private final HttpResponseHeaders httpResponseHeaders;
    private final HttpResponseBody body;
    private final Cookies cookies;

    public HttpResponse(Protocol protocol, HttpStatus httpStatus, Map<String, String> headers, String body,
        Set<Cookie> cookies) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
        this.httpResponseHeaders = new HttpResponseHeaders(headers, body.length());
        this.body = new HttpResponseBody(body);
        this.cookies = new Cookies(cookies);
    }

    public HttpResponse(HttpStatus status, Map<String, String> headers) {
        this(Protocol.of("HTTP/1.1"), status, headers, "", new HashSet<>());
    }

    public HttpResponse(HttpStatus status, Map<String, String> headers, Cookie cookie) {
        this(Protocol.of("HTTP/1.1"), status, headers, "", new HashSet<>(Arrays.asList(cookie)));
    }

    public static HttpResponse found(String location) {
        return new HttpResponse(HttpStatus.FOUND, Map.of("Location", location));
    }

    public static HttpResponse ok(String body) {
        return new HttpResponse(Protocol.of("HTTP/1.1"), HttpStatus.OK, Map.of(), body, new HashSet<>());
    }

    public static HttpResponse parseStaticFile(String url, String fileExtension) {
        var prefix = url.endsWith(".html") ? "./templates" : "./static";

        var bytes = FileIoUtils.loadFileFromClasspath(prefix, url);
        var contentType = ContentType.of(fileExtension);

        return new HttpResponse(
            Protocol.of("HTTP/1.1"),
            HttpStatus.OK,
            Map.of("Content-Type", contentType.getMessage()),
            new String(bytes, StandardCharsets.UTF_8),
            new HashSet<>()
        );
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getBody() {
        return body.getValue();
    }

    public Cookies getCookies() {
        return cookies;
    }

    public void write(DataOutputStream dataOutputStream) {
        writeHeader(dataOutputStream);
        writeBody(dataOutputStream);
    }

    public void putCookie(String key, String value) {
        cookies.putCookie(key, value);
    }

    private void writeHeader(DataOutputStream dos) {
        try {
            dos.writeBytes(String.format("%s/%s %d %s\r\n",
                protocol.getProtocolType(),
                protocol.getVersion(),
                httpStatus.getStatusCode(),
                httpStatus.getMessage())
            );

            httpResponseHeaders.write(dos);
            cookies.write(dos);

            dos.writeBytes("\r\n");
        } catch (IOException e) {
            throw new HttpResponseWriteException(e.getMessage());
        }
}

    private void writeBody(DataOutputStream dos) {
        try {
            dos.write(body.getValue().getBytes(StandardCharsets.UTF_8), 0, body.length());
            dos.flush();
        } catch (IOException e) {
            throw new HttpResponseWriteException(e.getMessage());
        }
    }

    public Optional<String> getCookie(String key) {
        return cookies.getCookie(key);
    }

    public Map<String, String> getHeaders() {
        return httpResponseHeaders.getHeaders();
    }

    public void putCookie(HttpSession session) {
        cookies.putCookie(DefaultHttpSession.SESSION_KEY, session.getSessionId());
    }
}
