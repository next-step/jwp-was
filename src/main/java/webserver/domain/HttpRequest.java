package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

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
