package webserver.http.domain;

import webserver.http.domain.exception.BadRequestException;

import java.util.regex.Pattern;

public class KeyValuePair {
    private static final int SPLIT_SIZE = 2;

    private static final String EMPTY_VALUE = "";

    private final String key;
    private final String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static KeyValuePair from(String message, String delimiter) {
        return from(message, delimiter, true);
    }
    public static KeyValuePair from(String message, String delimiter, boolean isValueRequired) {
        validate(message, delimiter, isValueRequired);

        String literalSplitPattern = Pattern.quote(delimiter);
        String[] splitMessage = message.split(literalSplitPattern);
        String key = splitMessage[0];
        if (isEmptyValue(splitMessage)) {
            return new KeyValuePair(key, EMPTY_VALUE);
        }
        String value = splitMessage[1];

        return new KeyValuePair(key, value);
    }

    private static void validate(String message, String delimiter, boolean isValueRequired) {
        validateEmpty(message);
        validateExistsKey(message, delimiter);
        validateKeyValueFormat(message, delimiter, isValueRequired);
    }

    private static void validateEmpty(String message) {
        if (message.isBlank()) {
            throw new BadRequestException("key, value 파싱을 위한 메시지가 공백이 될수 없습니다.");
        }
    }

    private static void validateExistsKey(String message, String delimiter) {
        if (message.startsWith(delimiter)) {
            throw new BadRequestException("메시지에 key가 반드시 존재해야 합니다.");
        }
    }

    private static void validateKeyValueFormat(String message, String delimiter, boolean isValueRequired) {
        String literalSplitPattern = Pattern.quote(delimiter);
        int splitCount = message.split(literalSplitPattern).length;
        if (isValueRequired) {
            validateValueRequiredFormat(splitCount);
            return;
        }

        validateValueNonRequiredFormat(splitCount);
    }

    private static void validateValueRequiredFormat(int splitCount) {
        if (splitCount == SPLIT_SIZE) {
            return;
        }
        throw new BadRequestException("'key=value' 방식의 값이 아닙니다.");
    }

    private static void validateValueNonRequiredFormat(int splitCount) {
        if (splitCount <= SPLIT_SIZE) {
            return;
        }
        throw new BadRequestException("'key[=value]' 방식의 값이 아닙니다.");
    }

    private static boolean isEmptyValue(String[] splitMessage) {
        return splitMessage.length < SPLIT_SIZE;
    }
}
