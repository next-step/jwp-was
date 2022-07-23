package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeaders {

    private final String HEADER_KEY_VALUE_SEPARATOR = ": ";

    private final Map<String, String> requestHeaders;

    public RequestHeaders(List<String> httpMessageData) {
        this.requestHeaders = new HashMap<>();
        if (httpMessageData.stream().anyMatch(data -> !data.contains(HEADER_KEY_VALUE_SEPARATOR))) {
            throw new IllegalArgumentException();
        }

        httpMessageData
                .forEach(data -> {
                    String[] values = data.split(HEADER_KEY_VALUE_SEPARATOR);
                    String key = values[0];
                    String value = values[1];
                    requestHeaders.put(key, value);
                });
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

}
