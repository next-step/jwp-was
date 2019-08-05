package webserver.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum GeneralHeaderFields {

    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    DATE("Date"),
    PRAGMA("Pragma"),
    TRAILER("Trailer"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    UPGRADE("Upgrade"),
    VIA("Via"),
    WARNING("Warning"),
    NONE("")
    ;

    private final String name;

    public static boolean contains(final String name) {
        return Arrays.stream(values())
                .anyMatch(field -> StringUtils.startsWith(name, field.getName()));
    }

    public static GeneralHeaderFields valueOfName(final String name) {
        return Arrays.stream(values())
                .filter(value -> value.name.equals(name))
                .findFirst()
                .orElse(NONE);
    }
}
