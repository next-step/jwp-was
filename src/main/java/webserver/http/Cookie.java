package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import javafx.util.Pair;
import utils.StringParseUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cookie {
    private static final String COOKIE_SEPARATOR = "; ";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private Map<String, String> cookieMap;

    public Cookie() {
        this.cookieMap = new HashMap<>();
    }

    private Cookie(Map<String, String> cookieMap) {
        this.cookieMap = cookieMap;
    }

    public static Cookie parse(String cookieString) {
        if (StringUtils.isBlank(cookieString))
            return new Cookie(new HashMap<>());

        Map<String, String> cookieMap = Stream.of(cookieString.split(COOKIE_SEPARATOR))
                .filter(StringUtils::isNotBlank)
                .map(headerLine -> StringParseUtils.makeKeyValuePair(headerLine, KEY_VALUE_SEPARATOR))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        return new Cookie(cookieMap);
    }

    public String get(String key) {
        return this.cookieMap.get(key);
    }

    public void set(String key, String value) {
        this.cookieMap.put(key, value);
    }

    public Set<String> keySet() {
        return this.cookieMap.keySet();
    }

    public boolean isEmpty() {
        return this.cookieMap.isEmpty();
    }
}
