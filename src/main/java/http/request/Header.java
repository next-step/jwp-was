package http.request;


import http.HeaderField;
import http.HeaderFieldName;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;

public class Header {
    private final Map<String, HeaderField> header;

    public Header(Map<String, HeaderField> header) {
        this.header = header;
    }

    public String getValue(HeaderFieldName headerFieldName) {
        return getValue(headerFieldName.stringify());
    }

    public String getValue(String headerFieldName) {
        HeaderField headerField = header.get(headerFieldName);
        if (headerField == null) {
            return Strings.EMPTY;
        }
        return headerField.getValue();
    }
}
