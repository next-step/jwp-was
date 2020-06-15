package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static http.response.HttpResponseHeaderKeys.CONTENT_LENGTH_HEADER_KEY;
import static http.response.HttpResponseHeaderKeys.CONTENT_TYPE_HEADER_KEY;
import static http.response.HttpResponseHeaderKeys.COOKIE_HEADER_KEY;
import static http.response.HttpStatusCode.OK;

public class HttpResponseMetaData {

    private static final String NEW_LINE = "\r\n";
    private static final String HTTP_HEADER_DELIMITER = ":";

    private HttpStatusCode statusCode = OK;
    private Map<String, String> headers = new HashMap<>();
    private List<Cookie> cookies = new ArrayList<>();
    private byte[] responseBody = {};

    public HttpResponseMetaData() {
        updateContentType("text/html", 0);
    }

    public void updateContentType(String mimeType, int length) {
        headers.put(CONTENT_TYPE_HEADER_KEY, mimeType);
        headers.put(CONTENT_LENGTH_HEADER_KEY, String.valueOf(length));
    }

    public void updateResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }

    public void updateStatusCode(HttpStatusCode statusCode) {
        if (statusCode != null) {
            this.statusCode = statusCode;
        }
    }

    public void addCookie(Cookie cookie) {
        if (cookie != null) {
            this.cookies.add(cookie);
        }
    }

    public void putResponseHeader(String key, String value) {
        headers.put(key, value);
    }

    public void writeResponseLine(DataOutputStream dos, String protocolSpec) throws IOException {
        dos.writeBytes(String.format("%s %s %s ", protocolSpec, statusCode.getCode(), statusCode.name()) + NEW_LINE);
    }

    public void writeResponseHeaders(DataOutputStream dos) throws IOException {
        for (Cookie cookie : cookies) {
            dos.writeBytes(generateHeaderLine(COOKIE_HEADER_KEY, cookie.generateCookieHeader()) + NEW_LINE);
        }

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            dos.writeBytes(generateHeaderLine(entry.getKey(), entry.getValue()) + NEW_LINE);
        }
    }

    public void writeResponseBody(DataOutputStream dos) throws IOException {
        if (responseBody.length > 0) {
            dos.writeBytes(NEW_LINE);
            dos.write(responseBody, 0, responseBody.length);
        }
    }

    String generateHeaderLine(String key, String value) {
        return String.format("%s%s %s", key, HTTP_HEADER_DELIMITER, value);
    }
}
