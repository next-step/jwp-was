package http;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders extends LinkedMultiValueMap<String, String> {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String LOCATION = "Location";
    public static final String ACCEPT = "Accept";
    public static final String USER_AGENT = "User-Agent";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String COOKIE = "Cookie";

    private HttpHeaders(MultiValueMap<String, String> headers) {
        super(headers);
    }

    public static HttpHeaders from(String headerString) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Arrays.stream(headerString.split("\n"))
                .map(header -> header.split(": "))
                .forEach(nameValue -> headers.add(nameValue[0], nameValue[1]));

        return new HttpHeaders(headers);
    }

    public static HttpHeaders emptyHeaders() {
        return new HttpHeaders(new LinkedMultiValueMap<>());
    }

    public String getHeader(String name) {
        List<String> values = get(name);
        return values == null ? null : values.get(0);
    }

    public List<String> getHeaders(String name) {
        return get(name);
    }
}
