package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class QueryString {

    private Map<String, String> parameters;

    private QueryString(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    static QueryString of(String parameters) {
        String[] dataes = parameters.split("&");

        return Arrays.stream(dataes)
                .collect(collectingAndThen(toMap(data -> data.split("=")[0], data -> data.split("=")[1]), QueryString::new));
    }

    String get(String attribute) {
        return parameters.get(attribute);
    }

    Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    @Override
    public String toString() {
        return parameters.entrySet()
                .stream()
                .map((parameter) -> parameter.getKey() + "=" + parameter.getValue())
                .collect(Collectors.joining("&"));
    }
}
