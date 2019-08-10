package webserver.http;

import utils.StringUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

public class HttpResponse {

    private DataOutputStream dos;
    private Map<String, Object> attributes;
    private Cookies cookies;

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        cookies = new Cookies(new HashMap<>());
        attributes = new HashMap<>();
    }

    public void addCookie(String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            cookies.addCookie(key, value);
        }
    }

    public String getCookie(String key) {
        return cookies.getCookie(key);
    }

    public void addAttributes(String key, Object value) {
        attributes.put(key, value);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void writeLocation(String location) throws IOException {
        dos.writeBytes("Location: " + location + "\r\n");
    }

    public void writeStatusLine(HttpStatusCode code) throws IOException {
        dos.writeBytes("HTTP/1.1 " + code.getCode() + " " + code.getMessage() + " \r\n");
    }

    public void writeContentType(String contentType) throws IOException {
        dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
    }

    public void writeContentLength(int lengthOfBodyContent) throws IOException {
        dos.writeBytes(lengthOfBodyContent > 0 ? "Content-Length: " + lengthOfBodyContent + "\r\n" : "");
    }

    public void writeCookies() throws IOException {
        dos.writeBytes(ofNullable(cookies.getCookies())
                .map(Map::entrySet)
                .map(entries -> entries.stream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                        .collect(joining(",")))
                .map(cookie -> "Set-Cookie: " + cookie + "; Path=/\r\n")
                .orElse(""));
    }

    public void writeRN() throws IOException {
        dos.writeBytes("\r\n");
    }

    public void writeBody(byte[] body) throws IOException {
        if (body.length > 0) {
            dos.write(body, 0, body.length);
        }

        dos.flush();
    }

}
