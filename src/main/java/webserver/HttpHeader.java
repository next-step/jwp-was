package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private Map<String, String> headerInfo = new HashMap<>();

    void add(String token) {
        Parameter parameter = Parameter.of(token);
        headerInfo.put(parameter.getField(), parameter.getValue());
    }

    String get(String field) {
        return headerInfo.get(field);
    }

    @Override
    public String toString() {
        return "HttpHeader{" +
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

        private Parameter(String field, String value) {
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
    }
}
