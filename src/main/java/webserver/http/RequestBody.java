package webserver.http;

import utils.MapUtils;
import utils.StringDecoder;

import java.util.Map;
import java.util.stream.Stream;

public class RequestBody {
    private static final String REQUEST_BODY_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private Map<String, String> requestBody;

    private RequestBody(Map<String, String> requestBody) {
        this.requestBody = requestBody;
    }

    public static RequestBody parse(String requestBodyString) {
        return new RequestBody(getRequestBody(requestBodyString));
    }

    private static Map<String, String> getRequestBody(String requestBodyString) {
        return MapUtils.keyValueMap(Stream.of(StringDecoder.decode(requestBodyString).split(REQUEST_BODY_SEPARATOR)), KEY_VALUE_SEPARATOR);
    }

    @Override
    public String toString() {
        return this.requestBody.toString();
    }

    public String get(String key) {
        return this.requestBody.get(key);
    }
}
