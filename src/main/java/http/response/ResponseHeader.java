package http.response;

import http.common.HeaderField;
import http.common.HeaderFieldName;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class ResponseHeader implements Iterable {

    private final Map<String, HeaderField> header;

    public ResponseHeader() {
        this.header = new HashMap<>();
    }

    public Optional<String> getValue(String headerName) {
        final HeaderField headerField = header.get(headerName);
        if (headerField == null) {
            return Optional.empty();
        }
        return Optional.of(headerField.getValue());
    }

    public Optional<String> getValue(HeaderFieldName headerName) {
        return getValue(headerName.stringify());
    }

    public void addHeader(HeaderField headerField) {
        this.header.put(headerField.getName(), headerField);
    }

    public void addHeader(String name, String value) {
        HeaderField headerField = new HeaderField(name, value);
        addHeader(headerField);
    }

    @Override
    public Iterator iterator() {
        return header.keySet().iterator();
    }
}
