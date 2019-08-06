package webserver.http;

import utils.StringUtils;

public final class StringPair extends Pair<String, String> {

    public StringPair(String key, String value) {
        super(validateKey(key), value);
    }

    public static final String EMPTY_VALUE = "";

    public static StringPair split(String target, String regex) {
        String[] keyValue = target.split(regex);
        String key = keyValue[0];
        return new StringPair(key, getValueOrDefault(keyValue));
    }

    public static StringPair split(String target, String regex, int splitLimit) {
        String[] keyValue = target.split(regex, splitLimit + 1);
        String key = keyValue[0];
        return new StringPair(key, getValueOrDefault(keyValue));
    }

    private static String validateKey(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("유효하지 않은 키입니다.");
        }
        return key;
    }

    private static String getValueOrDefault(String[] keyValue) {
        if (keyValue.length == 1) {
            return EMPTY_VALUE;
        }
        return keyValue[1];
    }
}
