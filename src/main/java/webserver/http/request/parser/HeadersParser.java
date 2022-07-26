package webserver.http.request.parser;

import webserver.http.request.Headers;
import webserver.http.request.KeyValuePair;

import java.util.List;
import java.util.stream.Collectors;

public class HeadersParser {
    public static final String KEY_VALUE_DELIMITER = ": ";

    private final KeyValuePairParser keyValuePairParser;

    public HeadersParser(KeyValuePairParser keyValuePairParser) {
        this.keyValuePairParser = keyValuePairParser;
    }

    public Headers parse(List<String> messages) {
        return messages.stream()
                .map(message -> keyValuePairParser.parse(message, KEY_VALUE_DELIMITER))
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(KeyValuePair::getKey, KeyValuePair::getValue),
                        Headers::new
                ));
    }
}