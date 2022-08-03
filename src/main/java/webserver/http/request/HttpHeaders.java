package webserver.http.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpHeaders {
    private static final String HEADER_DELIMITER = ": ";
    private static final Pattern HEADER_PATTERN = Pattern.compile("(.*)" + HEADER_DELIMITER + "(.*)");
    private final Map<String, String> headersByKey = new HashMap<>();

    public static HttpHeaders from(String headers) {
        HttpHeaders header = new HttpHeaders();
        Arrays.stream(headers.split("\n"))
                .map(HttpHeaders::parse)
                .forEach(header::put);
        return header;
    }

    private static List<String> parse(String header) {
        Matcher matcher = HEADER_PATTERN.matcher(header);
        if (!matcher.find()) {
            throw new IllegalArgumentException("잘못된 헤더입니다.");
        }

        return List.of(matcher.group(1), matcher.group(2));
    }

    private void put(List<String> keyValue) {
        put(keyValue.get(0), keyValue.get(1));
    }

    private void put(String key, String value) {
        headersByKey.put(key, value);
    }

    public String get(String key) {
        return headersByKey.get(key);
    }
}
