package http;

import http.response.ResponseHeader;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private StatusCode statusCode;
    private ContentType contentType = ContentType.HTML;
    private byte[] responseBody = new byte[0];

    private final Cookies cookies = Cookies.init();
    private final ResponseHeader header = ResponseHeader.init();
    private final Map<String, Object> models = new HashMap<>();

    private HttpResponse(final StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public static HttpResponse init() {
        return new HttpResponse(StatusCode.OK);
    }

    public void sendRedirect(final String location) {
        header.setLocation(location);
        statusCode = StatusCode.REDIRECT;
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
        return header.getLocation();
    }

    public void writeResponse(final OutputStream outputStream) {
        statusCode.writeResponse(this, new DataOutputStream(outputStream));
    }

    public void setCookie(final String key, final String value) {
        cookies.setCookie(key, value);
    }

    public Map<String, String> getCookies() {
        return cookies.getCookies();
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

    public void forward(final String path) {
        header.setForward(path);
    }

    public String getForward() {
        return header.getForward();
    }

    public void addModel(final String key, final Object value) {
        models.put(key, value);
    }

    public Map<String, Object> getModels() {
        return models;
    }
}
