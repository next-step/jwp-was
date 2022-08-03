package model;

import java.util.Arrays;
import java.util.List;

public class HttpResponse {

    private List<String> messages;
    private byte[] body;

    public HttpResponse(List<String> messages, byte[] body) {
        this.messages = messages;
        this.body = body;
    }

    public HttpResponse(List<String> messages) {
        this.messages = messages;
    }

    public static HttpResponse redirect(String location) {
        return new HttpResponse(Arrays.asList("HTTP/1.1 302 OK \r\n",
                "Location: http://localhost:8080" + location + "\r\n",
                "\r\n"));
    }

    public static HttpResponse success(byte[] body) {
        return new HttpResponse(Arrays.asList("HTTP/1.1 200 OK \r\n",
                "Content-Type: text/html;charset=utf-8\r\n",
                "Content-Length: " + body.length + "\r\n",
                "\r\n"), body);
    }

    public static HttpResponse loginRedirect(String location, boolean login) {
        return new HttpResponse(Arrays.asList("HTTP/1.1 302 OK \r\n",
                "Location: http://localhost:8080" + location + "\r\n",
                "Set-Cookie: logined=" + login + "; Path=/\r\n",
                "\r\n"));
    }

    public static HttpResponse successView(byte[] body, String contentType) {
        return new HttpResponse(Arrays.asList("HTTP/1.1 200 OK \r\n",
                "Content-Type: " + contentType + ";charset=utf-8\r\n",
                "Content-Length: " + body.length + "\r\n",
                "\r\n"), body);
    }

    public List<String> getMessages() {
        return messages;
    }

    public byte[] getBody() {
        return body;
    }

    public boolean hasBody() {
        return body != null && body.length > 0;
    }
}
