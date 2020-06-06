package http;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private final StatusCode statusCode;
    private final ContentType contentType;
    private final byte[] responseBody;
    private String location;
    private final Map<String, String> cookies = new HashMap<>();

    private HttpResponse(final StatusCode statusCode, final ContentType contentType, final byte[] responseBody) {
        this.statusCode = statusCode;
        this.contentType = contentType;
        this.responseBody = responseBody;
    }

    public static HttpResponse of(final StatusCode statusCode) {
        return new HttpResponse(statusCode, null, null);
    }

    public static HttpResponse ok(final ContentType contentType, final byte[] responseBody) {
        return new HttpResponse(StatusCode.OK, contentType, responseBody);
    }

    public static HttpResponse redirect(final String location) {
        HttpResponse httpResponse = new HttpResponse(StatusCode.REDIRECT, null, null);

        httpResponse.location = location;

        return httpResponse;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public int getStatusCode() {
        return statusCode.getCodeValue();
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
}
