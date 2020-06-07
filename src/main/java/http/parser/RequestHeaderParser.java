package http.parser;

import http.Header;
import http.Headers;

import java.util.*;

public class RequestHeaderParser {

    private static final String HEADER_NAME_VALUE_TOKENIZER = ":";

    public static Headers parse(List<String> headersStr) {
        Map<String, Header> headers = new HashMap<>();
        for (String headerStr : headersStr) {
            if (headerStr.isEmpty()) {
                continue;
            }
            String[] h = headerStr.split(HEADER_NAME_VALUE_TOKENIZER,2);
            String headerName = h[0].trim();
            String headerValue = h[1].trim();
            Header header = new Header(headerName, headerValue);
            headers.put(headerName, header);
        }
        return new Headers(headers);
    }
}
