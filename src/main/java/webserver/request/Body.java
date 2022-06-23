package webserver.request;

import utils.MapUtils;

import java.util.Map;

public class Body {

    private final Map<String, String> body;

    public Body(Map<String, String> body) {
        this.body = body;
    }

    public static Body from(String body) {
        Map<String, String> requestBodyMap = MapUtils.parse(body);
        return new Body(requestBodyMap);
    }

    public String get(String key) {
        return body.get(key);
    }
}
