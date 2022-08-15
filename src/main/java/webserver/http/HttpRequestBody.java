package webserver.http;

import utils.RequestUtils;

import java.util.Collections;
import java.util.Map;

public class HttpRequestBody {
    private final Map<String, String> body;

    private HttpRequestBody(Map<String, String> body) {
        this.body = body;
    }

    public static HttpRequestBody of(String readData) {
        return new HttpRequestBody(RequestUtils.createRequestDataMap(readData));
    }

    public static HttpRequestBody empty() {
        return new HttpRequestBody(Collections.emptyMap());
    }

    public String getValue(String value) {
        return body.get(value);
    }
}
