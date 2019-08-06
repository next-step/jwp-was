package webserver.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EntityHeaderFields {

    ALLOW("Allow"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_LANGUAGE("Content-Language"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_LOCATION("Content-Location"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    EXPIRES("Expires"),
    LAST_MODIFIED("Last-Modified"),
    NONE("")
    ;

    private final String name;

    public static boolean contains(final String name) {
        return Arrays.stream(values())
                .anyMatch(field -> StringUtils.contains(name, field.getName()));
    }

    public static boolean anyMatch(final String name) {
        return Arrays.stream(values())
                .anyMatch(field -> StringUtils.equals(name, field.getName()));
    }

    public static EntityHeaderFields valueOfName(final String name) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(name))
                .findFirst()
                .orElse(NONE);
    }
}
