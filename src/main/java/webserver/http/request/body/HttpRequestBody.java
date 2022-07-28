package webserver.http.request.body;

import webserver.http.request.HttpRequestParam;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpRequestBody {
    private static final String HTTP_REQUEST_BODY_DELIMITER = "&";
    private List<HttpRequestParam> bodys;

    public HttpRequestBody(String rawHttpRequestBody) {
        String[] httpRequestBodySchemas = toHttpRequestBodySchemas(rawHttpRequestBody);
        List<HttpRequestParam> bodys = Arrays.stream(httpRequestBodySchemas)
                .map(HttpRequestParam::from)
                .collect(Collectors.toList());
        this.bodys = bodys;
    }

    private String[] toHttpRequestBodySchemas(String rawHttpRequestBody) {
        if (rawHttpRequestBody == null || rawHttpRequestBody.isEmpty()) {
            return new String[]{};
        }

        return URLDecoder.decode(rawHttpRequestBody, StandardCharsets.UTF_8).split(HTTP_REQUEST_BODY_DELIMITER);
    }

    public String getBodyValue(String bodyName) {
        return bodys.stream()
                .filter(httpRequestBody -> bodyName.equals(httpRequestBody.getName()))
                .map(HttpRequestParam::getValue)
                .findAny()
                .orElse("");
    }
}
