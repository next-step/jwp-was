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

    public HttpHeader(LinkedHashMap<String, String> keyToValue) {
        this.keyToValue = keyToValue;
    }

    public HttpHeader(List<String> headers) {
        LinkedHashMap<String, String> keyToValue = new LinkedHashMap();

        for (String header : headers) {
            String[] split = header.split(HTTP_HEADER_SEPARATOR);

            keyToValue.put(split[HEADER_INDEX], split[VALUE_INDEX]);
        }

        this.keyToValue = keyToValue;
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
}
