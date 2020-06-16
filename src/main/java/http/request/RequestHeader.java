package http.request;


import http.common.HeaderField;
import http.common.HeaderFieldName;

import java.util.Map;
import java.util.Optional;

public class RequestHeader {
    private final Map<String, HeaderField> header;

    public RequestHeader(Map<String, HeaderField> header) {
        this.header = header;
    }

    public Optional<String> getValue(HeaderFieldName headerFieldName) {
        return getValue(headerFieldName.stringify());
    }

    public Optional<String> getValue(String headerFieldName) {
        String value = Optional.ofNullable(header.get(headerFieldName))
                .map(HeaderField::getValue)
                .orElse(null);
        return Optional.ofNullable(value);
    }
}
