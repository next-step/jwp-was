package webserver.request;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryString {

    private static final String PROPERTY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> properties;

    private QueryString(Map<String, String> properties) {
        this.properties = Collections.unmodifiableMap(properties);
    }

    public static QueryString from(Map<String, String> properties) {
        if (properties == null) {
            return empty();
        }
        return new QueryString(properties);
    }

    public static QueryString from(String string) {
        if (string == null || string.isBlank()) {
            return empty();
        }
        return from(parse(string));
    }

    public static QueryString empty() {
        return from(Collections.emptyMap());
    }

    private static Map<String, String> parse(String string) {
        return Stream.of(string.split(PROPERTY_DELIMITER))
                .filter(keyValue -> keyValue.contains(KEY_VALUE_DELIMITER))
                .map(QueryString::parseToOptionalEntry)
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map.Entry<String, String> parseToOptionalEntry(String keyValue) {
        String[] splitKeyValue = keyValue.split(KEY_VALUE_DELIMITER);
        return Map.entry(splitKeyValue[0], splitKeyValue[1]);
    }

    public Optional<String> value(String property) {
        if (properties.containsKey(property)) {
            return Optional.ofNullable(properties.get(property));
        }
        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryString that = (QueryString) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public String toString() {
        return "QueryString{" +
                "propertyValueMap=" + properties +
                '}';
    }
}
