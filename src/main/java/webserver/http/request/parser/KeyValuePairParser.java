package webserver.http.request.parser;

import webserver.http.request.KeyValuePair;

public class KeyValuePairParser {
    private static final int SPLIT_SIZE = 2;

    private static final String REPLACEMENT_DELIMITER_REGEX = " ";
    private static final String EMPTY_VALUE = "";

    public KeyValuePair parse(String message, String delimiter) {
        if (delimiter.length() != 1) {
            throw new RuntimeException("구분자 length는 1이어야 합니다.");
        }

        if (message.startsWith(delimiter)) {
            throw new RuntimeException("메시지에 key가 반드시 존재해야 합니다.");
        }

        int delimiterCount = 0;
        for (int i = 0; i < message.length(); i++) {
            if (delimiter.charAt(0) == message.charAt(i)) {
                delimiterCount++;
            }
        }
        if (delimiterCount >= SPLIT_SIZE) {
            throw new RuntimeException("한개보다 많은 구분자가 포함될수 없습니다.");
        }

        String[] splitMessage = message.replace(delimiter, REPLACEMENT_DELIMITER_REGEX).split(REPLACEMENT_DELIMITER_REGEX);
        String key = splitMessage[0];
        if (isEmptyValue(splitMessage)) {
            return new KeyValuePair(key, EMPTY_VALUE);
        }
        String value = splitMessage[1];

        return new KeyValuePair(key, value);
    }

    private boolean isEmptyValue(String[] splitMessage) {
        return splitMessage.length < SPLIT_SIZE;
    }
}
