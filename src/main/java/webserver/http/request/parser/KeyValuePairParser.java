package webserver.http.request.parser;

import webserver.http.request.KeyValuePair;

import java.util.regex.Pattern;

public class KeyValuePairParser {
    private static final int SPLIT_SIZE = 2;

    private static final String EMPTY_VALUE = "";

    private static final int DELIMITER_LENGTH = 1;

    public KeyValuePair<String, String> parse(String message, String delimiter) {
        validate(message, delimiter);

        String literalSplitPattern = Pattern.quote(delimiter);
        String[] splitMessage = message.split(literalSplitPattern);
        String key = splitMessage[0];
        if (isEmptyValue(splitMessage)) {
            return new KeyValuePair<>(key, EMPTY_VALUE);
        }
        String value = splitMessage[1];

        return new KeyValuePair<>(key, value);
    }

    private void validate(String message, String delimiter) {
        validateDelimiterLength(delimiter);
        validateExistsKey(message, delimiter);
        validateDelimiterCount(message, delimiter);
    }

    private void validateDelimiterLength(String delimiter) {
        if (delimiter.length() != DELIMITER_LENGTH) {
            throw new RuntimeException("구분자 length는 1이어야 합니다.");
        }
    }

    private void validateExistsKey(String message, String delimiter) {
        if (message.startsWith(delimiter)) {
            throw new RuntimeException("메시지에 key가 반드시 존재해야 합니다.");
        }
    }

    private void validateDelimiterCount(String message, String delimiter) {
        int delimiterCount = calculateDelimiterCount(message, delimiter);
        if (delimiterCount >= SPLIT_SIZE) {
            throw new RuntimeException("한개보다 많은 구분자가 포함될수 없습니다.");
        }
    }

    private int calculateDelimiterCount(String message, String delimiter) {
        int delimiterCount = 0;
        for (int i = 0; i < message.length(); i++) {
            if (delimiter.charAt(0) == message.charAt(i)) {
                delimiterCount++;
            }
        }
        return delimiterCount;
    }

    private boolean isEmptyValue(String[] splitMessage) {
        return splitMessage.length < SPLIT_SIZE;
    }
}
