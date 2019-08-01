package webserver.http;

import java.util.Arrays;

public enum RequestMethod {

    GET, POST;

    public static RequestMethod of(final String rawRequestMethod) {
        return Arrays.stream(values())
                .filter(requestMethod -> requestMethod.equalsName(rawRequestMethod))
                .findFirst()
                .orElseThrow();
    }

    private boolean equalsName(final String rawRequestMethod) {
        return name().equals(rawRequestMethod.toUpperCase());
    }
}
