package http.cookie;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Cookie {

    public static final String COOKIE_HEADER = "Cookie";
    public static final String COOKIE_VALUE_DELIMITER = "=";
    public static final String COOKIE_DELIMITER = "; ";

    private static final String MAX_AGE_KEY = "Max-Age";
    private static final String DOMAIN_KEY = "Domain";
    private static final String PATH_KEY = "Path";

    private final String name;
    private final String value;

    @Setter
    private Integer maxAge = null;

    @Setter
    private String domain;

    @Setter
    private String path;

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public String generateCookieHeader() {
        List<String> cookieValues = new ArrayList<>();
        cookieValues.add(generateCookiePair(name, value));
        cookieValues.add(generateCookiePair(MAX_AGE_KEY, maxAge));
        cookieValues.add(generateCookiePair(DOMAIN_KEY, domain));
        cookieValues.add(generateCookiePair(PATH_KEY, path));

        return cookieValues.stream()
                .filter(str -> !StringUtils.isEmpty(str))
                .collect(Collectors.joining(COOKIE_DELIMITER));
    }

    private String generateCookiePair(String key, Object value) {
        if (value == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(key)
                .append(COOKIE_VALUE_DELIMITER)
                .append(value);
        return builder.toString();
    }
}
