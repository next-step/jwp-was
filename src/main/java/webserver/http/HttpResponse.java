package webserver.http;

import utils.FileIoUtils;
import webserver.http.exception.WriteOutputStreamException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

import static utils.DelimiterConstants.*;

public class HttpResponse {
    private static final String CONTENT_TYPE_TEXT = "text/%s;charset=utf-8";
    private static final String HTML = "html";

    private HttpStatusCode code;
    private ResponseHeader header;
    private DataOutputStream dos;
    private byte[] body;

    private HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        this.header = ResponseHeader.empty();
    }

    private HttpResponse(HttpStatusCode httpStatusCode, ResponseHeader responseHeader, byte[] body) {
        this.code = httpStatusCode;
        this.header = responseHeader;
        this.body = body;
        writeResponse();
    }

    private void writeResponse() {
        try {
            writeHttpHeaders();
            dos.writeBytes(EMPTY_LINE);
            writeBody(body);
        } catch (IOException e) {
            throw new WriteOutputStreamException(e.getMessage());
        }
    }

    public HttpResponse sendRedirect(String path) {
        this.code = HttpStatusCode.FOUND;
        this.header.addHeader(HttpHeaders.LOCATION, path);
        this.body = new byte[0];
        writeResponse();
        return this;
    }

    public HttpResponse sendRedirectWithCookie(String path, String cookies) {
        this.code = HttpStatusCode.FOUND;
        this.header.addHeader(HttpHeaders.LOCATION, path);
        String[] splitCookies = cookies.split(COOKIE_VALUE_DELIMITER);
        for (String cookie : splitCookies) {
            String[] split = cookie.split(PARAMETER_KEY_VALUE_DELIMITER);
            this.header.addCookie(split[0], split[1]);
        }
        this.body = new byte[0];
        writeResponse();
        return this;
    }

    public HttpResponse forward(String filePath, String path) throws IOException, URISyntaxException {
        this.code = HttpStatusCode.OK;
        this.body = FileIoUtils.loadFileFromClasspath(filePath);
        this.header.addHeader(HttpHeaders.CONTENT_TYPE, String.format(CONTENT_TYPE_TEXT, fileExtension(path)));
        this.header.addHeader(HttpHeaders.CONTENT_LENGTH, body.length);
        writeResponse();
        return this;
    }

    public HttpResponse forwardBody(String body) {
        this.code = HttpStatusCode.OK;
        this.body = body.getBytes(StandardCharsets.UTF_8);
        this.header.addHeader(HttpHeaders.CONTENT_TYPE, String.format(CONTENT_TYPE_TEXT, HTML));
        this.header.addHeader(HttpHeaders.CONTENT_LENGTH, body.length());
        writeResponse();
        return this;
    }

    public static HttpResponse notFound() {
        return new HttpResponse(HttpStatusCode.NOT_FOUND, ResponseHeader.empty(), new byte[0]);
    }

    public static HttpResponse error() {
        return new HttpResponse(HttpStatusCode.INTERNAL_SERVER_ERROR, ResponseHeader.empty(), new byte[0]);
    }

    public static HttpResponse of(DataOutputStream dos) {
        return new HttpResponse(dos);
    }

    private static String fileExtension(String path) {
        return path.substring(path.lastIndexOf('.') + 1);
    }

    private void writeHttpHeaders() throws IOException {
        dos.writeBytes(String.format("HTTP/1.1 %s \r\n", getHttpResponseCode()));
        for (Map.Entry<HttpHeaders, Object> header : getHeaders()) {
            dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
        }

        if (containsCookie()) {
            dos.writeBytes(String.format("%s: %s \r\n", HttpHeaders.SET_COOKIE, getCookie()));
        }
    }

    private void writeBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public String getHttpResponseCode() {
        return code.getHttpResponseCode();
    }

    public byte[] getBody() {
        return body;
    }

    public Set<Map.Entry<HttpHeaders, Object>> getHeaders() {
        return header.getHeaders().entrySet();
    }

    public boolean containsCookie() {
        return header.containsCookie();
    }

    public String getCookie() {
        return header.getCookie();
    }

    public void addCookie(String cookieKey, String cookieValue) {
        header.addCookie(cookieKey,cookieValue);
    }
}
