package webserver.response;

import webserver.ContentType;
import webserver.request.Header;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponse {

    private static final String CRLF = "\r\n";

    private StatusLine statusLine;
    private Header header;
    private ResponseBody responseBody;

    private DataOutputStream dos;

    private HttpResponse(StatusLine statusLine, Header header, ResponseBody responseBody) {
        this.statusLine = statusLine;
        this.header = header;
        this.responseBody = responseBody;
    }

    public HttpResponse(OutputStream outputStream) {
        dos = new DataOutputStream(outputStream);
    }

    public static HttpResponse forward(String body, String mediaType) {
        StatusLine statusLine = StatusLine.parse("HTTP/1.1 200 OK");

        Header header = new Header();
        header.addHeader("Content-Type: " + mediaType);
        header.addHeader("Content-Length: " + body.length());

        ResponseBody responseBody = ResponseBody.parse(body);

        return new HttpResponse(statusLine, header, responseBody);
    }

    public static HttpResponse sendRedirect(String path, String cookie) {
        StatusLine statusLine = StatusLine.parse("HTTP/1.1 302 Found");

        Header header = new Header();
        header.addHeader("Location: " + path);
        if (!cookie.isEmpty()) {
            header.addHeader("Set-Cookie: " + cookie);
        }

        return new HttpResponse(statusLine, header, ResponseBody.parse(""));
    }

    public static HttpResponse notFound(String path) {
        return sendRedirect(path, "");
    }

    public byte[] getBytes() {
        String response = statusLine.getStatusLine() + CRLF;

        Map<String, String> map = header.getHeaderMap();
        for (String key : map.keySet()) {
            response += key + ": " + map.get(key) + CRLF;
        }
        response += CRLF;

        response += responseBody.getBody();

        return response.getBytes();
    }
}
