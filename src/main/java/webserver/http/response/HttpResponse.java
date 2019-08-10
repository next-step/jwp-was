package webserver.http.response;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class HttpResponse {

    private static final String COOKIE_FIELD_NAME = "Set-Cookie";
    private static final String HTTP_VERSION = "HTTP/1.1";
    private HttpStatus httpStatus;
    private Map<String, String> headers;
    private DataOutputStream outputStream;

    public HttpResponse(OutputStream out) {
        headers = new HashMap<>();
        this.outputStream = new DataOutputStream(out);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHttpVersion() {
        return HTTP_VERSION;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setContentType(ContentType contentType) {
        addHeader(ContentType.getHeaderFieldName(), contentType.getMediaType());
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public void setCookie(String cookie) {
        headers.put(COOKIE_FIELD_NAME, cookie);
    }
}
