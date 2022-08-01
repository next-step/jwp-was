package webserver.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static webserver.domain.Cookie.CookieAttribute.NONE;
import static webserver.domain.Cookie.CookieAttribute.PATH;

public class Cookie {
    public static final Cookie EMPTY = new Cookie();
    public static final String EMPTY_STR = "";
    public static final String COOKIE_ATTR_DELIMITER = "; ";
    public static final String EMPTY_COOKIE_ATTR_MAP_MSG = "쿠키 속성이 유효하지 않습니다.";

    enum CookieAttribute {
        EXPIRES("Expires", true),
        MAX_AGE("Max-Age", true),
        DOMAIN("Domain", true),
        PATH("Path", true),
        SECURE("Secure", false),
        HTTP_ONLY("HttpOnly", false),
        SAME_SITE("SameSite", true),
        NONE(null, false);

        private final String attributeName;
        private final boolean requiredValue;
        private static final Map<String, CookieAttribute> store;

        static {
            store = Arrays.stream(values())
                    .collect(toMap(c -> c.attributeName, c -> c));
        }

        CookieAttribute(String attributeName, boolean requiredValue) {
            this.attributeName = attributeName;
            this.requiredValue = requiredValue;
        }

        static CookieAttribute lineOf(String line) {
            String attributeName = line.split(KEY_VALUE_DELIMITER)[0];
            return store.getOrDefault(attributeName, NONE);
        }

        String getValueOrDefault(String[] values, String defaultValue) {
            if (this.requiredValue && values.length == 2) {
                return values[1];
            }

            return defaultValue;
        }

        public String attributeName() {
            return this.attributeName;
        }
    }

    public static final String KEY_VALUE_DELIMITER = "=";
    public static final String ATTRIBUTE_DELIMITER = "; ";

    private final String name;
    private final String value;
    private final Map<CookieAttribute, String> cookieAttMap = new EnumMap<>(CookieAttribute.class);


    public Cookie() {
        this(null, null, Collections.emptyMap());
    }

    public Cookie(String name, String value) {
        this(name, value, Collections.emptyMap());
    }

    public Cookie(String name, String value, Map<CookieAttribute, String> cookieAttMap) {
        if (Objects.isNull(cookieAttMap)) {
            throw new IllegalArgumentException(EMPTY_COOKIE_ATTR_MAP_MSG);
        }

        this.name = name;
        this.value = value;
        this.cookieAttMap.putAll(cookieAttMap);
    }


    public static Cookie from(String line) {
        if (line == null) {
            return new Cookie();
        }
        String[] tokens = line.split(ATTRIBUTE_DELIMITER);

        String[] nameValueTokens = tokens[0].split(KEY_VALUE_DELIMITER);

        if (tokens.length > 1) {
            return new Cookie(nameValueTokens[0],
                    nameValueTokens[1],
                    getCookieAttrMap(Arrays.copyOfRange(tokens, 1, tokens.length)));
        }

        return new Cookie(nameValueTokens[0], nameValueTokens[1]);

    }

    private static Map<CookieAttribute, String> getCookieAttrMap(String[] tokens) {
        Map<CookieAttribute, String> cookieAttrMap = new EnumMap<>(CookieAttribute.class);

        for (String token : tokens) {
            String[] values = token.split(KEY_VALUE_DELIMITER);

            CookieAttribute cookieAttribute = CookieAttribute.lineOf(values[0]);

            cookieAttrMap.put(cookieAttribute, cookieAttribute.getValueOrDefault(values, EMPTY_STR));
        }

        return cookieAttrMap;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setPath(String path) {
        cookieAttMap.put(PATH, path);
    }

    public void addAttribute(String key, String value) {
        CookieAttribute ca = CookieAttribute.lineOf(key);
        if (!NONE.equals(ca)) {
            cookieAttMap.put(ca, value);
        }
    }

    @Override
    public String toString() {
        return name + KEY_VALUE_DELIMITER + value +
                COOKIE_ATTR_DELIMITER +
                cookieAttMap.entrySet().stream()
                .map(entry -> entry.getKey().attributeName() + KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(COOKIE_ATTR_DELIMITER));
    }

    public String getAttribute(String key) {
        CookieAttribute ca = CookieAttribute.lineOf(key);
        if (NONE.equals(ca)) {
            return null;
        }
        return cookieAttMap.getOrDefault(ca, null);
    }

    public Map<CookieAttribute, String> getStore() {
        return Collections.unmodifiableMap(cookieAttMap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cookie)) {
            return false;
        }
        Cookie cookie = (Cookie) o;
        return Objects.equals(name, cookie.name) && Objects.equals(value, cookie.value) && Objects.equals(cookieAttMap, cookie.cookieAttMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, cookieAttMap);
    }
}
