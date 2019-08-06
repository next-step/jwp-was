package webserver;

import webserver.response.HeaderProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static webserver.response.HeaderProperty.*;

public class HttpHeaders {

    public static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";

    private Map<String, String> headerInfo = new HashMap<>();

    public void add(String token) {
        Parameter parameter = Parameter.of(token);
        addHeader(parameter.getField(), parameter.getValue());
    }

    public void setLocation(String path) {
        addHeader(LOCATION.getHeaderName(), path);
    }

    public void setContentType(String value) {
        addHeader(CONTENT_TYPE.getHeaderName(), value);
    }

    public void setContentLength(int contentLength) {
        addHeader(CONTENT_LENGTH.getHeaderName(), String.valueOf(contentLength));
    }

    public int getContentLength() {
        return Integer.parseInt(headerInfo.getOrDefault(CONTENT_LENGTH.getHeaderName(), "-1"));
    }

    private void addHeader(String key, String value) {
        headerInfo.put(key, value);
    }

    public String get(HeaderProperty headerProperty) {
        return get(headerProperty.getHeaderName());
    }

    public String get(String headerName) {
        return headerInfo.get(headerName);
    }

    public void setCookie(String value) {
        headerInfo.put(HeaderProperty.SET_COOKIE.getHeaderName(), value);
    }

    public List<String> output() {
        return headerInfo.entrySet()
                .stream()
                .map(entry -> new Parameter(entry.getKey(), entry.getValue()))
                .map(Parameter::toString)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headerInfo=" + headerInfo +
                '}';
    }

    static class Parameter {

        private static final int INDEX_OF_ATTRIBUTE = 0;
        private static final int INDEX_OF_VALUE = 1;
        private static final int SIZE = 2;
        private static final String SEPARATOR = ": ";

        private String field;
        private String value;

        Parameter(String field, String value) {
            this.field = field;
            this.value = value;
        }

        static Parameter of(String parameter) {
            if (parameter == null) {
                throw new IllegalArgumentException("Input value should not be null");
            }

            String[] pair = parameter.split(SEPARATOR);
            if (pair.length < SIZE) {
                throw new IllegalArgumentException(String.format("헤더 정보값이 잘못 입력하였습니다. 입력값: %s", parameter));
            }
            return new Parameter(pair[INDEX_OF_ATTRIBUTE], pair[INDEX_OF_VALUE]);
        }

        String getField() {
            return field;
        }

        String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return field + SEPARATOR + value;
        }
    }
}
