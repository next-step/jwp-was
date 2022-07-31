package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Contents {

    private static final String PARAM_DELIMITER = "&";

    private Map<String, String> contents;

    private Contents() {
        this.contents = new HashMap<>();
    }

    private Contents(Map<String, String> Contents) {
        this.contents = Contents;
    }

    public static Contents from(String values) {
        return parseContents(values);
    }

    public static Contents from(Map<String, String> Contents) {
        return new Contents(Contents);
    }

    public Map<String, String> getContents() {
        return Collections.unmodifiableMap(contents);
    }

    private static Contents parseContents(String values) {
        if (values.isEmpty()) {
            return new Contents();
        }
        String[] tokens = values.split(PARAM_DELIMITER);
        return new Contents(Arrays.stream(tokens)
                .map(KeyValue::of)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue)));
    }

    public String getContent(String key) {
        return contents.get(key);
    }
}
