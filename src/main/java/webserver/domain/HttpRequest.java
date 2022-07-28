package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Http 요청 정보
 */
public class HttpRequest {

    public static final int MIN_CONTENT_LENGTH = 0;
    private final RequestLine requestLine;
    private final HttpHeaders headers;
    private final RequestBody requestBody;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public HttpRequest(@JsonProperty("requestLine") RequestLine requestLine,
                       @JsonProperty("headers") HttpHeaders headers,
                       @JsonProperty("requestBody") RequestBody requestBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.requestBody = requestBody;
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

        return new HttpRequest(requestLine, httpHeaders, requestBody);
    }

    /**
     * 요청 정보를 반환한다.
     */
    public RequestLine getRequestLine() {
        return requestLine;
    }

    /**
     * 요청 헤더 정보를 반환한다.
     */
    public HttpHeaders getHeaders() {
        return headers;
    }

    /**
     * 요청 정보 body를 반환한다.
     */
    public RequestBody getRequestBody() {
        return requestBody;
    }

    /**
     * 쿠키 정보를 반환한다.
     */
    public Cookie getCookie() {
        return headers.getCookie();
    }

    @Override
    public String toString() {
        return String.format(
                "%s \r\n " +
                        "%s \r\n " +
                        "%s \r\n", requestLine, headers, requestBody);
    }
}
