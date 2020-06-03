package http;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryString {

    private final Map<String, String> map;

    public QueryString(String raw) {
        this.map = Arrays.stream(raw.split("&"))
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
}
