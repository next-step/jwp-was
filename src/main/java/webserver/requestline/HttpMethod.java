package webserver.requestline;

import exception.ResourceNotFound;

import java.util.Arrays;

public enum HttpMethod {

    GET,
    POST,
    PUT,
    PATCH,
    DELETE;

    public static HttpMethod from(String version){
        return Arrays.stream(values())
                .filter(value -> value.name().equals(version))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound());
    }
}
