package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class Cookie {

    private Map<String, String> cookies;

    private Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookie of(String token) {
        if (token == null || token.isEmpty()) {
            return new Cookie(Collections.emptyMap());
        }

        return Arrays.stream(token.split("; "))
                .map(Parameter::of)
                .collect(collectingAndThen(toMap(Parameter::getAttribute, Parameter::getValue), Cookie::new));
    }


    public String get(String key) {
        return cookies.get(key);
    }

    static class Parameter {

        private static final int INDEX_OF_ATTRIBUTE = 0;
        private static final int INDEX_OF_VALUE = 1;
        private static final String SEPARATOR = "=";

        private String attribute;
        private String value;

        private Parameter(String attribute, String value) {
            this.attribute = attribute;
            this.value = value;
        }

        static Parameter of(String parameter) {
            String[] pair = parameter.split(SEPARATOR);
            return new Parameter(pair[INDEX_OF_ATTRIBUTE], pair[INDEX_OF_VALUE]);
        }

        String getAttribute() {
            return attribute;
        }

        String getValue() {
            return value;
        }
    }
}
