package http;

import http.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import utils.StringUtils;

import java.util.AbstractMap;
import java.util.Map;

import static http.HttpHeaders.HEADER_KEY_VALUE_SPLITTER;

public class HttpHeader {
    public static final String CONTENT_LENGTH_NAME = "Content-Length";
    public static final String CONTENT_TYPE_NAME = "Content-Type";

    private final Map.Entry<String, String> header;

    public HttpHeader(String name, String value) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
            throw new BadRequestException();
        }

        this.header = new AbstractMap.SimpleEntry<>(name, value);
    }

    public static HttpHeader of(String httpHeaderLine) {
        if (StringUtils.isEmpty(httpHeaderLine)) {
            throw new BadRequestException();
        }

        String[] split = httpHeaderLine.split(HEADER_KEY_VALUE_SPLITTER, 2);
        return new HttpHeader(split[0], split[1]);
    }

    public String getHeaderName() {
        return header.getKey();
    }

    public String getHeaderValue() {
        return header.getValue();
    }
}
