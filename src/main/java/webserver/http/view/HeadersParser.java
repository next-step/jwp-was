package webserver.http.view;

import webserver.http.domain.Headers;
import webserver.http.domain.KeyValuePair;

import java.util.LinkedHashMap;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class HeadersParser {
    public static final String KEY_VALUE_DELIMITER = ": ";

    private final KeyValuePairParser keyValuePairParser;

    public HeadersParser(KeyValuePairParser keyValuePairParser) {
        this.keyValuePairParser = keyValuePairParser;
    }

    public Headers parse(List<String> messages) {
        return messages.stream()
                .map(message -> keyValuePairParser.parse(message, KEY_VALUE_DELIMITER))
                .collect(collectingAndThen(
                        toMap(
                                KeyValuePair::getKey,
                                KeyValuePair::getValue,
                                (oldValue, newValue) -> newValue,
                                LinkedHashMap::new
                        ),
                        Headers::new
                ));
    }
}
