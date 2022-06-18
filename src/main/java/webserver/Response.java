package webserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Response {

    private final HttpStatus httpStatus;
    private final String contentType;
    private final String path;

    public Response(final String contentType, final String path) {
        this(HttpStatus.OK, contentType, path);
    }

    public Response(final HttpStatus httpStatus, final String contentType, final String path) {
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.path = path;
    }

    public String getContentType() {
        return contentType;
    }

    public String getPath() {
        return path;
    }

    public byte[] getBytes() throws IOException {
        byte[] body = Files.readAllBytes(Paths.get("./webapp/" + path));
        byte[] header = getHeader(body.length);
        return getBytes(header, body);
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

        stringBuilder.append("\r\n");

        return stringBuilder.toString().getBytes();
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
}
