package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpMethod;

public class HttpRequest {
    public static final int REQUEST_LINE_POINT = 0;
    public static final int HEADER_START_POINT = 1;
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

    public static HttpRequest newInstance(String line) {
        String[] attributes = line.split(System.lineSeparator());

        RequestLine requestLine = RequestLine.from(attributes[REQUEST_LINE_POINT]);

        if (HttpMethod.POST.equals(requestLine.getMethod())) {
            return new HttpRequest(requestLine,
                    HttpHeaders.newInstance(attributes, HEADER_START_POINT, attributes.length),
                    new RequestBody(attributes[attributes.length - 1]));
        }

        return new HttpRequest(requestLine,
                HttpHeaders.newInstance(attributes, HEADER_START_POINT, attributes.length - 1),
                null);
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
}
