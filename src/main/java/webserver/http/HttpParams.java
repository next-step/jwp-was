package webserver.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpParams {

    private final Map<String, String> params;

    public HttpParams() {
        this.params = new HashMap<>();
    }

    public HttpParams(String querystring) {
        this.params = toParams(querystring);
    }

    private Map<String, String> toParams(String querystring) {
        return Arrays.stream(querystring.split("&"))
                .map(this::parseKeyValue)
                .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
    }

    private String[] parseKeyValue(String q) {
        String[] keyValue = q.split("=");
        if (keyValue.length != 2) {
            throw new IllegalArgumentException("invalid key=value: " + q);
        }
        return keyValue;
    }
}
