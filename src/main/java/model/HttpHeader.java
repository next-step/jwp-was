package model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class HttpHeader {
    public static final String HTTP_HEADER_SEPARATOR = ": ";
    private static final String COOKIE_SEPARATOR = "; ";
    private static final Integer HEADER_INDEX = 0;
    private static final Integer VALUE_INDEX = 1;

    private final LinkedHashMap<String, String> keyToValue;

    private HttpHeader(Builder builder) {
        this.keyToValue = builder.keyToValue;
    }

    public static class Builder {
        private final LinkedHashMap<String, String> keyToValue;

        public Builder() {
            this.keyToValue = new LinkedHashMap();
        }

        public Builder addHeader(String header) {
            String[] split = header.split(HTTP_HEADER_SEPARATOR);

            keyToValue.put(split[HEADER_INDEX], split[VALUE_INDEX]);

            return this;
        }

        public Builder addHeaders(List<String> headers) {
            for (String header : headers) {
                String[] split = header.split(HTTP_HEADER_SEPARATOR);

                this.keyToValue.put(split[HEADER_INDEX], split[VALUE_INDEX]);
            }

            return this;
        }

        public Builder sendRedirect(String path) {
            keyToValue.put("Location", "http://localhost:8080" + path);

            return this;
        }

        public Builder addSession(String sessionId) {
            String cookie = keyToValue.get("Set-Cookie");

            if (keyToValue.get("Set-Cookie") == null) {
                keyToValue.put("Set-Cookie", "JSESSIONID=" + sessionId + COOKIE_SEPARATOR);

                return this;
            }

            keyToValue.replace("Set-Cookie", "JSESSIONID=" + sessionId + COOKIE_SEPARATOR + cookie);
            return this;

        }

        public HttpHeader build() {
            return new HttpHeader(this);
        }
    }

    public String getValueByKey(String key) {
        return keyToValue.get(key);
    }

    public List<String> getHttpHeaders() {
        return keyToValue
            .entrySet()
            .stream()
            .map(entry -> entry.getKey() + HTTP_HEADER_SEPARATOR + entry.getValue())
            .collect(Collectors.toList());
    }

    public boolean hasCookie(String cookie) {
        return Arrays.asList(getValueByKey("Cookie").split(COOKIE_SEPARATOR))
            .contains(cookie);
    }

    public boolean hasSession() {
        if (getValueByKey("Cookie") != null) {

            return Arrays.asList(getValueByKey("Cookie").split(COOKIE_SEPARATOR))
                .contains("JSESSIONID");
        }

        return false;
    }

    public String getSessionId() {
        return Arrays.stream(getValueByKey("Cookie")
                .split(COOKIE_SEPARATOR))
            .filter(it -> it.contains("JSESSIONID"))
            .findFirst()
            .orElse("")
            .replace("JSESSIONID=", "");
    }
}
