package webserver.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RequestHeaderFields {

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

    public static RequestHeaderFields valueOfName(final String name) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(name))
                .findFirst()
                .orElse(NONE);
    }
}
