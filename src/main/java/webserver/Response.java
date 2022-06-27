package webserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Response {

    private final HttpStatus httpStatus;
    private final MediaType contentType;
    private final String path;
    private final String cookie;

    private final String body;

    public Response(final MediaType contentType, final String path, final String cookie) {
        this(HttpStatus.OK, contentType, path, cookie, null);
    }

    public Response(final MediaType contentType, final String path, final String cookie, final String body) {
        this(HttpStatus.OK, contentType, path, cookie, body);
    }

    public Response(final String contentType, final String path, final String cookie) {
        this(HttpStatus.OK, new MediaType(contentType), path, cookie, null);
    }

    public Response(final String contentType, final String path, final String cookie, final String body) {
        this(HttpStatus.OK, new MediaType(contentType), path, cookie, body);
    }

    public Response(final HttpStatus httpStatus, final MediaType contentType, final String path, final String cookie) {
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.path = path;
        this.cookie = cookie;
        this.body = null;
    }

    public Response(final HttpStatus httpStatus, final MediaType contentType, final String path, final String cookie, final String body) {
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.path = path;
        this.cookie = cookie;
        this.body = body;
    }

    public MediaType getContentType() {
        return contentType;
    }

    public String getPath() {
        return path;
    }

    public byte[] getBytes() throws IOException {
        byte[] body = getBody();
        byte[] header = getHeader(body.length);
        return getBytes(header, body);
    }

    private byte[] getBody() throws IOException {
        if (body != null) {
            return body.getBytes();
        }
        return Files.readAllBytes(Paths.get("./webapp/" + path));
    }

    private byte[] getBytes(byte[] header, byte[] body) {
        byte[] responseBytes = new byte[header.length + body.length];
        System.arraycopy(header, 0, responseBytes, 0, header.length);
        System.arraycopy(body, 0, responseBytes, header.length, body.length);
        return responseBytes;
    }

    private byte[] getHeader(long bodyLength) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s %s \r\n", "HTTP/1.1", getStatus().toString()));
        stringBuilder.append(String.format("Content-Type: %s \r\n", contentType));
        stringBuilder.append(String.format("Content-Length: %s \r\n", bodyLength));
        stringBuilder.append(String.format("Location: %s \r\n", path));

        if (cookie != null) {
            stringBuilder.append(String.format("Set-Cookie: %s \r\n", cookie));
        }

        stringBuilder.append("\r\n");

        return stringBuilder.toString().getBytes();
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    public String getCookie() {
        return cookie;
    }
}
