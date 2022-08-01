package webserver.domain;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Http 요청 정보
 */
public class HttpRequest extends HttpEntity<RequestBody> {

    public static final int MIN_CONTENT_LENGTH = 0;
    private final RequestLine requestLine;

    public HttpRequest(RequestLine requestLine) {
        this(null, null, requestLine);
    }

    public HttpRequest(RequestBody body, RequestLine requestLine) {
        this(null, body, requestLine);
    }

    public HttpRequest(HttpHeaders headers, RequestBody body, RequestLine requestLine) {
        super(headers, body);
        this.requestLine = requestLine;
    }

    /**
     * Reader에서 Http Request 정보를 가져와 객체를 생성한다.
     *
     * @param br Http Request 정보를 담은 객체
     * @return Http 요청 정보
     */
    public static HttpRequest newInstance(BufferedReader br) throws IOException {
        String line = URLDecoder.decode(br.readLine(), StandardCharsets.UTF_8);
        RequestLine requestLine = RequestLine.from(line);
        RequestBody requestBody = RequestBody.fromRequestLine(line);
        HttpHeaders httpHeaders = HttpHeaders.from(br);

        int contentLength = httpHeaders.getContentLength();
        if (contentLength > MIN_CONTENT_LENGTH) {
            requestBody.addAttributes(IOUtils.readData(br, contentLength));
        }

        return new HttpRequest(httpHeaders, requestBody, requestLine);
    }

    /**
     * 요청 정보를 반환한다.
     */
    public RequestLine getRequestLine() {
        return requestLine;
    }

    /**
     * 요청 정보 body를 반환한다.
     */
    public RequestBody getRequestBody() {
        return getBody();
    }

    /**
     * 쿠키 정보를 반환한다.
     */
    public Cookie getCookie() {
        return getHeaders().getCookie();
    }

    public boolean hasCookie() {
        return !Cookie.EMPTY.equals(getHeaders().getCookie());
    }

    @Override
    public String toString() {
        return String.format(
                "%s \r\n " +
                        "%s \r\n " +
                        "%s \r\n", requestLine, getHeaders(), getBody());
    }

    public HttpSession getSession() {
        Cookie cookie = getCookie();
        String sessionId = cookie.getValue();
        SessionManager sessionManager = SessionManager.getInstance();

        if (Objects.isNull(sessionId) || sessionId.isEmpty()) {
            return sessionManager.createSession();
        }

        return sessionManager.findBySessionId(cookie.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(requestLine, that.requestLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), requestLine);
    }
}
