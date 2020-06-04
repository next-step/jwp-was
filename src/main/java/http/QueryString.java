package http;

import java.util.*;
import java.util.stream.Collectors;

public class QueryString {

    // http에 공통적으로 사용되는 delimiter를 나중에 상수로 모아서 분리하자
    private static final String QUERY_STRING_DELIMITER_REGEX = "\\?";
    private static final String KEY_VALUE_DELIMITER = "&";

    private final Map<String, String> map;

    public QueryString(String pathString) {
        this.map = pathString.contains("?") ?
                parseQueryString(pathString) :
                new HashMap<>();
    }

    private Map<String, String> parseQueryString(String pathString) {
        final String[] pathAndQuery = pathString.split(QUERY_STRING_DELIMITER_REGEX);
        return Arrays.stream(pathAndQuery[1].split(KEY_VALUE_DELIMITER))
                .map(this::splitParameter)
                .collect(Collectors.toMap(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        AbstractMap.SimpleImmutableEntry::getValue)
                );
    }

    public String getParameter(String attributeName) {
        return map.get(attributeName);
    }

    private AbstractMap.SimpleImmutableEntry<String, String> splitParameter(String raw) {
        final String[] keyAndValue = raw.split("=");
        return new AbstractMap.SimpleImmutableEntry<>(keyAndValue[0], keyAndValue[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
