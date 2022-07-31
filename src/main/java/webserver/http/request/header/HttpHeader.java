package webserver.http.request.header;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    private static final String HEADER_FIELD_DELIMITER = ":";
    private static final String CONTENT_LENGTH_STRING = "Content-Length";
    private static final String ZERO_STRING = "0";
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;

    private Map<String, String> field;

    public HttpHeader(Map<String, String> field) {
        this.field = field;
    }

    public HttpHeader() {
        this(new HashMap<>());
    }

    public void setField(String headerString) {
        validateHeaderString(headerString);
        String[] fieldElements = headerString.split(HEADER_FIELD_DELIMITER);
        field.put(fieldElements[HEADER_KEY_INDEX], fieldElements[HEADER_VALUE_INDEX].trim());
    }

    public int getContentLength() {
        return Integer.parseInt(field.getOrDefault(CONTENT_LENGTH_STRING, ZERO_STRING));
    }

    private void validateHeaderString(String headerString) {
        if (headerString == null || headerString.isEmpty()) {
            throw new IllegalArgumentException("요청된 HTTP Header 는 비어있거나 null 일 수 없습니다.");
        }
    }
}
