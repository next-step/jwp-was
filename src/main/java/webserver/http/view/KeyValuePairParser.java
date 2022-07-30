package webserver.http.view;

import webserver.http.domain.KeyValuePair;

import java.util.regex.Pattern;

public class KeyValuePairParser {
    private static final int SPLIT_SIZE = 2;

    private static final String EMPTY_VALUE = "";

    public KeyValuePair<String, String> parse(String message, String delimiter) {
        return parse(message, delimiter, true);
    }
    public KeyValuePair<String, String> parse(String message, String delimiter, boolean isValueRequired) {
        validate(message, delimiter, isValueRequired);

        String literalSplitPattern = Pattern.quote(delimiter);
        String[] splitMessage = message.split(literalSplitPattern);
        String key = splitMessage[0];
        if (isEmptyValue(splitMessage)) {
            return new KeyValuePair<>(key, EMPTY_VALUE);
        }
        String value = splitMessage[1];

        return new KeyValuePair<>(key, value);
    }

    private void validate(String message, String delimiter, boolean isValueRequired) {
        validateEmpty(message);
        validateExistsKey(message, delimiter);
        validateKeyValueFormat(message, delimiter, isValueRequired);
    }

    private void validateEmpty(String message) {
        if (message.isBlank()) {
            throw new RuntimeException("key, value 파싱을 위한 메시지가 공백이 될수 없습니다.");
        }
    }

    private void validateExistsKey(String message, String delimiter) {
        if (message.startsWith(delimiter)) {
            throw new RuntimeException("메시지에 key가 반드시 존재해야 합니다.");
        }
    }

    private void validateKeyValueFormat(String message, String delimiter, boolean isValueRequired) {
        String literalSplitPattern = Pattern.quote(delimiter);
        int splitCount = message.split(literalSplitPattern).length;
        if (isValueRequired) {
            validateValueRequiredFormat(splitCount);
            return;
        }

        validateValueNonRequiredFormat(splitCount);
    }

    private void validateValueRequiredFormat(int splitCount) {
        if (splitCount == SPLIT_SIZE) {
            return;
        }
        throw new RuntimeException("'key=value' 방식의 값이 아닙니다.");
    }

    private void validateValueNonRequiredFormat(int splitCount) {
        if (splitCount <= SPLIT_SIZE) {
            return;
        }
        throw new RuntimeException("'key[=value]' 방식의 값이 아닙니다.");
    }

    private boolean isEmptyValue(String[] splitMessage) {
        return splitMessage.length < SPLIT_SIZE;
    }
}
