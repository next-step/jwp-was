package http;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpHeaders extends LinkedHashMap<String, String> {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ACCEPT = "Accept";
    public static final String USER_AGENT = "User-Agent";

    private HttpHeaders(Map<String, String> headers) {
        super(headers);
    }

    public static HttpHeaders from(String headerString) {
        Map<String, String> headers = new LinkedHashMap<>();
        Arrays.stream(headerString.split("\n"))
                .map(header -> header.split(": "))
                .forEach(keyValue -> headers.put(keyValue[0], keyValue[1]));

        return new HttpHeaders(headers);
    }

    public String getHeader(String key) {
        return get(key);
    }

}
