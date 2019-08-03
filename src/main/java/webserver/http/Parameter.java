package webserver.http;

import utils.HttpStringType;
import utils.HttpStringUtils;

public class Parameter {
    private String key;
    private String value;

    private Parameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Parameter newInstance(String data) {
        String[] keyAndValue = HttpStringUtils.split(data, HttpStringType.DELIMITER_EQUAL_SIGN.getType());
        return new Parameter(keyAndValue[0], keyAndValue[1]);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
