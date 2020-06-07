package http;

import utils.StringUtil;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private StatusCode statusCode;
    private ContentType contentType = ContentType.HTML;
    private byte[] responseBody = new byte[0];
    private String location;
    private final Map<String, String> cookies = new HashMap<>();

    private HttpResponse(final StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public static HttpResponse init() {
        return new HttpResponse(StatusCode.OK);
    }

    public void sendRedirect(final String location) {
        if (StringUtil.isEmpty(location)) {
            throw new IllegalArgumentException("Redirect path is null or empty");
        }

        statusCode = StatusCode.REDIRECT;
        this.location = location;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public int getBodyLength() {
        return responseBody.length;
    }

    public byte[] getBody() {
        return responseBody;
    }

    public String getLocation() {
        return location;
    }

    public void writeResponse(final OutputStream outputStream) {
        statusCode.writeResponse(this, new DataOutputStream(outputStream));
    }

    public void setCookie(final String key, final String value) {
        cookies.put(key, value);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void updateStatus(final StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void updateBody(final byte[] responseBody) {
        this.responseBody = responseBody;
    }

    public void updateType(final ContentType contentType) {
        this.contentType = contentType;
    }
}
