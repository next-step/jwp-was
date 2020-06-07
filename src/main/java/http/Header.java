package http;

import java.util.HashMap;
import java.util.Map;

public class Header {
    private static final String HEADER_NAME_VALUE_TOKENIZER = ":";

    private Map<String, String> data = new HashMap<>();

    public Header(String[] headers) {
        for (String header : headers) {
            String[] h = header.split(HEADER_NAME_VALUE_TOKENIZER,2);
            String headerName = h[0].trim();
            String headerValue = h[1].trim();
            data.put(headerName, headerValue);
        }
    }

    public String getValue(String headerName) {
        return data.get(headerName);
    }
}
