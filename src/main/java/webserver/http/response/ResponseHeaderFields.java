package webserver.http.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ResponseHeaderFields {

    ACCEPT_RANGES("Accept-Ranges"),
    AGE("Age"),
    ETAG("ETag"),
    LOCATION("Location"),
    PROXY_AUTHENTICATE("Proxy-Authenticate"),
    RETRY_AFTER("Retry-After"),
    SERVER("Server"),
    VARY("Vary"),
    WWW_AUTHENTICATE("WWW-Authenticate"),
    NONE("")
    ;

    private final String name;

    public static ResponseHeaderFields valueOfName(final String name) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(name))
                .findFirst()
                .orElse(NONE);
    }
}
