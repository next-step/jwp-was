package webserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {

    static final String CONTENT_LENGTH = "Content-Length";
    static final String LOCATION = "Location";

    private Map<String, String> headerInfo = new HashMap<>();

    void add(String token) {
        Parameter parameter = Parameter.of(token);
        headerInfo.put(parameter.getField(), parameter.getValue());
    }

    public String get(String field) {
        return headerInfo.get(field);
    }

    int getContentLength() {
        return Integer.parseInt(headerInfo.getOrDefault(CONTENT_LENGTH, "-1"));
    }

    void setLocation(String path) {
        headerInfo.put(LOCATION, path);
    }

    void setContentLength(int contentLength) {
        headerInfo.put(CONTENT_LENGTH, String.valueOf(contentLength));
    }

    List<String> output() {
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

    public void add(String s, String s1) {
        headerInfo.put(s, s1);
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
