package webserver.request;

import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class QueryString {

    static final QueryString EMPTY = new QueryString(Maps.newHashMap());

    private static final String SEPARATOR = "&";

    private Map<String, String> parameters;

    private QueryString(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    static QueryString of(String queryString) {
        if (queryString == null || StringUtils.isEmpty(queryString.trim())) {
            return EMPTY;
        }
        String[] parameters = queryString.split(SEPARATOR);

        return Arrays.stream(parameters)
                .map(Parameter::of)
                .collect(collectingAndThen(toMap(Parameter::getAttribute, Parameter::getValue),
                        QueryString::new));
    }

    String get(String attribute) {
        return parameters.get(attribute);
    }

    Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    @Override
    public String toString() {
        return "QueryString{" +
                "parameters=" + parameters +
                '}';
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
            if (parameter == null || parameter.isEmpty()) {
                throw new IllegalArgumentException("Input value should not be null");
            }

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
