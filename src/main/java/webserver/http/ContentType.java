package webserver.http;

import java.util.Arrays;

import static utils.StringUtils.removeWhiteSpace;

public enum ContentType {

    TEXT_CSS("text/css"),
    TEXT_HTML_UTF_8("text/html;charset=utf-8"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");

    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public static ContentType parse(String value) {
        return Arrays.stream(values())
                .filter(contentType -> contentType.value.equalsIgnoreCase(removeWhiteSpace(value)))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported Content-Type"));
    }

    public String getValue() {
        return value;
    }
}