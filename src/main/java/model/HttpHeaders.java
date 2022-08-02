package model;

import types.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {

    private final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public HttpHeaders(List<String> httpMessageData) {
        this.headers = new HashMap<>();
        if (httpMessageData.stream().anyMatch(data -> !data.contains(HEADER_KEY_VALUE_SEPARATOR))) {
            throw new IllegalArgumentException();
        }

        httpMessageData
                .forEach(data -> {
                    String[] values = data.split(HEADER_KEY_VALUE_SEPARATOR);
                    String key = values[0];
                    String value = values[1];
                    headers.put(key, value);
                });
    }

    public String getInfo() {
        StringBuilder data = new StringBuilder();
        data.append("RequestHeaders : \n");
        this.headers.forEach((key, value) -> {
            data.append(String.format("key = %s || value = %s", key, value));
            data.append("\n");
        });

        return data.toString();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setLocation(String url) {
        this.headers.put("Location", url);
    }

    public void setContentType(MediaType mediaType) {
        this.headers.put("Content-Type", mediaType.getValue());
    }

    public void setCookie(String value) {
        this.headers.put("Set-Cookie", value);
    }
}
