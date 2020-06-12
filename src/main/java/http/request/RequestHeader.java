package http.request;


import http.common.HeaderField;
import http.common.HeaderFieldName;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;

public class RequestHeader {
    private static final String REQUEST_HEADER_DEFAULT_VALUE = Strings.EMPTY;

    private final Map<String, HeaderField> header;

    public RequestHeader(Map<String, HeaderField> header) {
        this.header = header;
    }

    public String getValue(HeaderFieldName headerFieldName) {
        return getValue(headerFieldName.stringify());
    }

    public String getValue(String headerFieldName) {
        final HeaderField headerField = header.get(headerFieldName);
        if (headerField == null) {
            return REQUEST_HEADER_DEFAULT_VALUE;
        }
        return headerField.getValue();
    }
}
