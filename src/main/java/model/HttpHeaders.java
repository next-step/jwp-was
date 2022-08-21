package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpHeaders {
    private static final String HEADER_DELIMITER = ": ";
    private static final Pattern HEADER_PATTERN = Pattern.compile("(.*)" + HEADER_DELIMITER + "(.*)");
    private static final String NEXT_LINE = "\n";

    private final Map<String, String> header = new HashMap<>();

    public HttpHeaders (String headers) {
        Arrays.stream(headers.split(NEXT_LINE))
                .map(HttpHeaders::parse)
                .forEach(value -> header.put(value.get(0), value.get(1)));
    }

    private static List<String> parse(String header) {
        Matcher matcher = HEADER_PATTERN.matcher(header);
        if (!matcher.find()) {
            throw new IllegalArgumentException("잘못된 헤더입니다.");
        }

        return Arrays.asList(matcher.group(1), matcher.group(2));
    }

    public String get(String key) {
        return header.get(key);
    }
}
