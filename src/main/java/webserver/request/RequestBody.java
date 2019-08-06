package webserver.request;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class RequestBody {

    public static final RequestBody EMPTY = new RequestBody(Maps.newHashMap());

    static final String SEPARATOR = "&";

    private final Map<String, String> parameters;

    private RequestBody(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static RequestBody parse(String request) {
        return Arrays.stream(request.split(SEPARATOR))
                .map(Parameter::of)
                .collect(collectingAndThen(toMap(Parameter::getField, Parameter::getValue),
                        RequestBody::new));
    }

    public String get(String field) {
        return parameters.get(field);
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "parameters=" + parameters +
                '}';
    }

    static class Parameter {

        private static final int INDEX_OF_ATTRIBUTE = 0;
        private static final int INDEX_OF_VALUE = 1;
        private static final int SIZE = 2;
        private static final String SEPARATOR = "=";

        private String field;
        private String value;

        private Parameter(String field, String value) {
            this.field = field;
            this.value = value;
        }

        static Parameter of(String parameter) {
            if (parameter == null) {
                throw new IllegalArgumentException("Input value should not be null");
            }

            String[] pair = parameter.split(SEPARATOR);
            if (pair.length < SIZE) {
                throw new IllegalArgumentException(String.format("RequestBody 잘못된 입력값입니다. 입력값: %s", parameter));
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