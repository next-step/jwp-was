package webserver.http;

import utils.HttpStringType;
import utils.HttpStringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Parameters {
    private Map<String, Parameter> parameters;

    private Parameters() {
        parameters = new HashMap<>();
    }

    private Parameters(String queryString) {
        parameters = Arrays.stream(HttpStringUtils.split(queryString, HttpStringType.DELIMITER_AMPERSAND.getType()))
                .map(Parameter::newInstance).collect(Collectors.toMap(p -> p.getKey(), p -> p));
    }

    public static Parameters newInstance(String queryString) {
        return Optional.of(queryString)
                .filter(input -> HttpStringUtils.isPatternMatch(HttpStringType.QUERYSTRING_PATTERN.getType(), input))
                .map(Parameters::new)
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Parameters emptyInstance() {
        return new Parameters();
    }

    public boolean isEmpty() {
        return parameters.size() == 0;
    }

    public String findByKey (String key) {
        return parameters.get(key).getValue();
    }

    public Map<String, Parameter> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }
}
