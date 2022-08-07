package http.response;

import exception.Assert;
import http.HttpHeader;
import http.request.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private StatusLine statusLine;
    private HttpHeader responseHeader;
    private ResponseBody responseBody;
    private DataOutputStream dos;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
    }

    public void buildResponse(StatusLine statusLine, HttpHeader responseHeader) {
        Assert.notNull(statusLine, "StatusLine은 null이어선 안됩니다.");
        Assert.notNull(responseHeader, "Header는 null이어선 안됩니다.");
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.responseBody = new ResponseBody(new byte[0]);
    }

    public void buildResponse(StatusLine statusLine, HttpHeader responseHeader, byte[] body) {
        Assert.notNull(statusLine, "StatusLine은 null이어선 안됩니다.");
        Assert.notNull(responseHeader, "Header는 null이어선 안됩니다.");
        Assert.notNull(body, "ResonseBody는 null이어선 안됩니다.");
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.responseBody = new ResponseBody(body);
    }

    public void buildResponse(StatusLine statusLine, HttpHeader responseHeader, String body) {
        Assert.notNull(statusLine, "StatusLine은 null이어선 안됩니다.");
        Assert.notNull(responseHeader, "Header는 null이어선 안됩니다.");
        Assert.notNull(body, "ResonseBody는 null이어선 안됩니다.");
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.responseBody = new ResponseBody(body.getBytes(StandardCharsets.UTF_8));
    }

    public HttpStatusCode getHttpStatusCode() {
        return statusLine.getHttpStatusCode();
    }

    public byte[] body() {
        return responseBody.getBody();
    }

    public int getContentLength() {
        return responseBody.getContentLength();
    }

    public String getResponseHeader(String key) {
        return responseHeader.get(key);
    }

    public Set<Map.Entry<String, String>> headerEntries() {
        return responseHeader.entries();
    }

    public Set<Map.Entry<String, String>> cookieEntries() {
        return responseHeader.getCookies();
    }

    public void writeResponse() {
        try {
            dos.writeBytes(String.format("%s %s \r%n", statusLine.getProtocolToString(), getHttpStatusCode()));
            writeHeader();
            writeCookies();
            writeBody();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeader() throws IOException {
        for (Map.Entry<String, String> entry : headerEntries()) {
            dos.writeBytes(String.format("%s: %s\r%n", entry.getKey(), entry.getValue()));
        }
    }

    private void writeCookies() throws IOException {
        if (cookieEntries().isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : cookieEntries()) {
            String cookieValue = entry.getKey() + "=" + entry.getValue();
            dos.writeBytes(String.format("%s: %s\r%n", HttpHeader.SET_COOKIE, cookieValue));
        }
        dos.writeBytes("\r\n");
    }

    private void writeBody() throws IOException {
        dos.write(body(), 0, getContentLength());
        dos.flush();
    }

    public void sendRedirect(String path) {
        sendRedirect(path, HttpHeader.empty());
    }

    public void sendRedirect(String path, HttpHeader header) {
        buildResponse(StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.FOUND),
                header.add(HttpHeader.LOCATION, path));
    }

    public void addHeader(String header, String value) {
        if (this.responseHeader == null) {
            this.responseHeader = HttpHeader.empty();
        }
        this.responseHeader.add(header, value);
    }

    public void notFound() {
        buildResponse(StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.NOT_FOUND),
                HttpHeader.empty());
    }
}
