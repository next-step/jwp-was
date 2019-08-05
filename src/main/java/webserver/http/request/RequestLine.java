package webserver.http.request;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import webserver.http.HttpMethod;
import webserver.http.HttpVersion;

import java.util.Objects;

/**
 * https://tools.ietf.org/html/rfc2616#section-5.1
 *
 * 5.1 Request-Line
 *
 * The Request-Line begins with a method token, followed by the
 * Request-URI and the protocol version, and ending with CRLF. The
 * elements are separated by SP characters. No CR or LF is allowed
 * except in the final CRLF sequence.
 *
 *      Request-Line   = Method SP Request-URI SP HTTP-Version CRLF
 */
@Getter
public class RequestLine {

    private static final String TOKEN = " ";

    private HttpMethod method;
    private RequestURI requestURI;
    private HttpVersion httpVersion;

    public static RequestLine parse(final String requestLine) {
        if (StringUtils.isBlank(requestLine)) {
            throw new IllegalArgumentException("Request-Line은 필수입니다.");
        }

        final String[] splitRequestLine = requestLine.split(TOKEN);
        final int INDEX_OF_METHOD = 0;
        final int INDEX_OF_REQUEST_URI = 1;
        final int INDEX_OF_HTTP_VERSION = 2;

        return new RequestLine(splitRequestLine[INDEX_OF_METHOD],
                               splitRequestLine[INDEX_OF_REQUEST_URI],
                               splitRequestLine[INDEX_OF_HTTP_VERSION]);
    }

    private RequestLine(final String method, final String requestURI, final String httpVersion) {
        setMethod(HttpMethod.valueOf(method));
        setRequestURI(requestURI);
        setHttpVersion(HttpVersion.valueOfVersion(httpVersion));
    }

    private void setMethod(HttpMethod method) {
        Objects.requireNonNull(method, "Method는 필수입니다.");

        this.method = method;
    }

    private void setRequestURI(String requestURI) {
        if (StringUtils.isBlank(requestURI)) {
            throw new IllegalArgumentException("Request-URI는 필수입니다.");
        }

        this.requestURI = new RequestURI(requestURI);
    }

    private void setHttpVersion(HttpVersion httpVersion) {
        Objects.requireNonNull(httpVersion, "HTTP-Version은 필수입니다.");

        this.httpVersion = httpVersion;
    }

    public String path() {
        return requestURI.path();
    }

    public Query query() {
        return requestURI.getQuery();
    }
}
