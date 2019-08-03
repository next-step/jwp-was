package webserver.http;

import utils.HttpStringType;
import utils.HttpStringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Parameters {
    private List<Parameter> parameters;

    private Parameters() {
        parameters = new ArrayList<>();
    }

    private Parameters(String queryString) {
        parameters = Arrays.stream(HttpStringUtils.split(queryString, HttpStringType.DELIMITER_AMPERSAND.getType()))
                .map(Parameter::newInstance).collect(Collectors.toList());
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

    public List<Parameter> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    public boolean isEmpty() {
        return parameters.size() == 0;
    }
}
