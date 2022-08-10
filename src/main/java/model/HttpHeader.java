package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class HttpHeader {

    public static final String DELIMITER = ":";
    public static final int NAME_INDEX = 0;
    public static final int VALUE_INDEX = 1;
    public static final int NO_CONTENT = 0;
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final int REQUEST_LINE_INDEX = 0;
    public static final String COOKIE = "Cookie";
    public static final String NO_COOKIE = null;
    public static final String HOST = "Host";
    public static final int PORT_INDEX = 2;

    private List<String> headers;

    public HttpHeader(List<String> headers) {
        this.headers = headers;
    }

    public int getContentLength() {
        return headers.stream()
                .map(header -> header.split(DELIMITER))
                .filter(header -> header[NAME_INDEX].equals(CONTENT_LENGTH))
                .map(header -> Integer.parseInt(header[VALUE_INDEX].strip()))
                .findFirst()
                .orElse(NO_CONTENT);
    }

    public String getRequestLine() {
        return headers.get(REQUEST_LINE_INDEX);
    }

    public String getCookie() {
        return headers.stream()
                .map(header -> header.split(DELIMITER))
                .filter(header -> header[NAME_INDEX].equals(COOKIE))
                .map(header -> header[VALUE_INDEX].strip())
                .findFirst()
                .orElse(NO_COOKIE);
    }

    public String getHost() {
        return headers.stream()
                .map(header -> header.split(DELIMITER))
                .filter(header -> header[NAME_INDEX].equals(HOST))
                .map(header -> header[VALUE_INDEX].strip() + DELIMITER + header[PORT_INDEX].strip())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("호스트 정보가 없습니다."));
    }

    public void addEndHeader() {
        headers.add("\r\n");
    }

    public void addRedirectStatus() {
        headers.add("HTTP/1.1 302 OK \r\n");
    }

    public void addRedirectLocation(String location) {
        headers.add("Location: "+  location + "\r\n");
    }

    public void addContentLength(int length) {
        headers.add("Content-Length: " + length + "\r\n");
    }

    public void addContentType(String contentType) {
        headers.add("Content-Type: " + contentType + ";charset=utf-8\r\n");
    }

    public void addSuccessStatus() {
        headers.add("HTTP/1.1 200 OK \r\n");
    }

    public void addCookie(String name, String value) {
        headers.add("Set-Cookie: " + name + "=" + value + "; Path=/\r\n");
    }

    public void writeOutput(DataOutputStream dos) {
        headers.stream()
                .forEach(header -> {
                    try {
                        dos.writeBytes(header);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public String toString() {
        return "HttpHeader{" +
                "headers=" + headers +
                '}';
    }
}
