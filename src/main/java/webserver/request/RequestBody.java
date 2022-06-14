package webserver.request;

import utils.MapUtils;

import java.util.Map;

public class RequestBody {

    private final Map<String, String> requestBody;

    public RequestBody(Map<String, String> requestBody) {
        this.requestBody = requestBody;
    }

    public static RequestBody from(String body) {
        Map<String, String> requestBodyMap = MapUtils.parse(body);
        return new RequestBody(requestBodyMap);
    }

    public String get(String key) {
        return requestBody.get(key);
    }
}
