package http.httpresponse;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class HttpResponse {
    private static final HttpResponse NOT_FOUND = new HttpResponse(HttpStatusCode.NOT_FOUND, ResponseHeader.empty());
    private static final HttpResponse INTERNAL_SERVER_ERROR = new HttpResponse(HttpStatusCode.INTERNAL_SERVER_ERROR, ResponseHeader.empty());

    private final HttpStatusCode httpStatusCode;
    private final ResponseHeader responseHeader;
    private final byte[] body;

    public HttpResponse(HttpStatusCode httpStatusCode,
                        ResponseHeader responseHeader,
                        byte[] body) {
        this.httpStatusCode = httpStatusCode;
        this.responseHeader = responseHeader;
        this.body = body;
    }

    public HttpResponse(HttpStatusCode httpStatusCode, ResponseHeader responseHeader) {
        this(httpStatusCode, responseHeader, new byte[0]);
    }

    public HttpResponse(HttpStatusCode httpStatusCode,
                        ResponseHeader responseHeader,
                        String body) {

        this(httpStatusCode, responseHeader, body.getBytes(StandardCharsets.UTF_8));
    }

    public static HttpResponse sendRedirect(String path) {
        return  sendRedirect(path, new ResponseHeader(Collections.emptyMap()));
    }

    public static HttpResponse sendRedirect(String path, ResponseHeader responseHeader) {
        return new HttpResponse(HttpStatusCode.FOUND, responseHeader.add(HttpHeaders.LOCATION, path));
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public byte[] getBody() {
        return body;
    }

    public int getContentLength() {
        return body.length;
    }

    public static HttpResponse notFound() {
        return NOT_FOUND;
    }

    public static HttpResponse internpalServerError() {
        return INTERNAL_SERVER_ERROR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpResponse that = (HttpResponse) o;
        return httpStatusCode == that.httpStatusCode && responseHeader.equals(that.responseHeader) && Arrays.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(httpStatusCode, responseHeader);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }
}
