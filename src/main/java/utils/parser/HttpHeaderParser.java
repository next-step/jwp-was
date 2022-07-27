package utils.parser;

import model.HttpHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaderParser {
    private static final Integer HEADER_INDEX = 0;
    private static final Integer VALUE_INDEX = 1;
    private static final String HEADER_SEPARATOR = ": ";

    public static HttpHeader parseHeader(List<String> headers) {
        Map<String, String> valueByHeader = new HashMap();

        for (String header : headers) {
            String[] split = header.split(HEADER_SEPARATOR);

            valueByHeader.put(split[HEADER_INDEX], split[VALUE_INDEX]);
        }

        return new HttpHeader(valueByHeader);
    }
}
