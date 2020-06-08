package http.common;

import http.common.HeaderFieldName;

public class HeaderField {
    private final String name;
    private final String value;

    public HeaderField(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public HeaderField(HeaderFieldName headerFieldName, String value) {
        this.name = headerFieldName.stringify();
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
