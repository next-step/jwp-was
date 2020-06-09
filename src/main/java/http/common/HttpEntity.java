package http.common;

import lombok.Getter;
import utils.MapUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

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
        return Optional.ofNullable(body)
            .map(MapUtil::buildParameters)
            .orElse(Collections.emptyMap());
    }

    public String getHeaderValue(String key) {
        return Optional.ofNullable(httpHeaders)
            .map(headers -> headers.getHeaderValue(key))
            .orElse("");
    }
}
