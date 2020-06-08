package http.response;

import http.ContentType;
import http.Cookies;
import http.StatusCode;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class HttpResponse {
    public static final String NEW_LINE = "\r\n";
    private StatusCode statusCode;
    private ContentType contentType = ContentType.HTML;
    private byte[] responseBody = new byte[0];

    private final Cookies cookies = Cookies.init();
    private final ResponseHeader header = ResponseHeader.newInstance();
    private final Models models = Models.init();
    private final Forward forward = new Forward();

    private HttpResponse(final StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public static HttpResponse init() {
        return new HttpResponse(StatusCode.OK);
    }

    public void writeResponse(final OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        writeResponseLine(dataOutputStream);
        writeContentType(dataOutputStream);
        writeContentLength(dataOutputStream);
        writeHeaders(dataOutputStream);
        writeCookies(dataOutputStream);
        writeNewLine(dataOutputStream);
        writeBody(dataOutputStream);
    }

    private void writeCookies(final DataOutputStream dataOutputStream) throws IOException {
        cookies.writeCookies(dataOutputStream);
    }

    private void writeContentType(final DataOutputStream dataOutputStream) throws IOException {
        if (statusCode.isOK()) {
            dataOutputStream.writeBytes("Content-Type: " + contentType.getContentTypeStr() + ";charset=utf-8\r\n");
        }
    }

    private void writeContentLength(final DataOutputStream dataOutputStream) throws IOException {
        if (responseBody.length > 0) {
            dataOutputStream.writeBytes("Content-Length: " + responseBody.length + " \r\n");
        }
    }

    private void writeBody(final DataOutputStream dataOutputStream) throws IOException {
        if (responseBody.length > 0) {
            dataOutputStream.write(responseBody, 0, responseBody.length);
        }
    }

    private void writeNewLine(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(NEW_LINE);
    }

    private void writeHeaders(final DataOutputStream dataOutputStream) throws IOException {
        header.writeHeaders(dataOutputStream);
    }

    private void writeResponseLine(final DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeBytes(statusCode.getResponseLine() + NEW_LINE);
    }

    public void setRedirect(final String location) {
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

    public void setForward(final String path) {
        forward.setForward(path);
    }

    public String getForward() {
        return forward.getForward();
    }

    public void addModel(final String key, final Object value) {
        models.addModel(key, value);
    }

    public Map<String, Object> getModels() {
        return models.getModels();
    }

    public boolean isForward() {
        return forward.isForward();
    }
}
