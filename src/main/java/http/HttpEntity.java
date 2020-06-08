package http;

import lombok.Getter;
import utils.MapParameterUtil;

import java.util.Map;

public class HttpEntity {
    @Getter
    private final HttpHeaders httpHeaders;

    @Getter
    private final String body;

    public HttpEntity(HttpHeaders httpHeaders) {
        this(httpHeaders, null);
    }

    public HttpEntity(HttpHeaders httpHeaders, String body) {
        this.httpHeaders = httpHeaders;
        this.body = body;
    }

    public Map<String, String> getBodyMap() {
        return MapParameterUtil.buildParameters(body);
    }
}
