package http;

import http.exception.BadRequestException;
import http.exception.HttpException;
import utils.StringUtils;

import java.util.AbstractMap;
import java.util.Map;

public class HttpHeader {
    private static final String HEADER_KEY_VALUE_SPLITTER = ": ";

    private final Map.Entry<String, String> header;

    public static HttpHeader of(String httpHeaderLine) {
        if (StringUtils.isEmpty(httpHeaderLine)) {
            throw new BadRequestException();
        }

        String[] split = httpHeaderLine.split(HEADER_KEY_VALUE_SPLITTER, 2);
        return new HttpHeader(split[0], split[1]);
    }

    public HttpHeader(String name, String value) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
            throw new BadRequestException();
        }

        this.header = new AbstractMap.SimpleEntry<>(name, value);
    }

    public String getHeaderName() {
        return header.getKey();
    }

    public String getHeaderValue() {
        return header.getValue();
    }
}
