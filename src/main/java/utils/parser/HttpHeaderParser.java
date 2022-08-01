package utils.parser;

import model.HttpHeader;

import java.util.LinkedHashMap;
import java.util.List;

import static model.HttpHeader.HTTP_HEADER_SEPARATOR;

public class HttpHeaderParser {
    private static final Integer HEADER_INDEX = 0;
    private static final Integer VALUE_INDEX = 1;

    public static HttpHeader parseHeader(List<String> headers) {
        LinkedHashMap<String, String> valueByHeader = new LinkedHashMap();

        for (String header : headers) {
            String[] split = header.split(HTTP_HEADER_SEPARATOR);

            valueByHeader.put(split[HEADER_INDEX], split[VALUE_INDEX]);
        }

        return new HttpHeader(valueByHeader);
    }
}
