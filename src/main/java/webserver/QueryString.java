package webserver;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class QueryString {

    static final QueryString EMPTY = new QueryString(emptyMap());

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

    static class Parameter {

        private static final String SEPARATOR = "=";

        private String attribute;
        private String value;

        private Parameter(String attribute, String value) {
            this.attribute = attribute;
            this.value = value;
        }

        static Parameter of(String parameter) {
            String[] pair = parameter.split(SEPARATOR);
            return new Parameter(pair[0], pair[1]);
        }

        String getAttribute() {
            return attribute;
        }

        String getValue() {
            return value;
        }
    }
}
