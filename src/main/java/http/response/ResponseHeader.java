package http.response;

import http.common.HeaderField;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResponseHeader implements Iterable {
    private final Map<String, HeaderField> header;

    public ResponseHeader() {
        this.header = new HashMap<>();
    }

    public String getValue(String headerName) {
        HeaderField headerField = header.get(headerName);
        if (headerField == null) {
            return Strings.EMPTY;
        }
        return headerField.getValue();
    }

    public void addHeader(HeaderField headerField) {
        this.header.put(headerField.getName(), headerField);
    }

    @Override
    public Iterator iterator() {
        return header.keySet().iterator();
    }
}
