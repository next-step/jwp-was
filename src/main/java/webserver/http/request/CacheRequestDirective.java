package webserver.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import webserver.http.CacheDirective;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CacheRequestDirective implements CacheDirective {

    NO_CACHE("no-cache"),
    NO_STORE("no-store"),
    MAX_AGE("max-age"),
    MAX_STALE("max-stale"),
    MIN_FRESH("min-fresh"),
    NO_TRANSFORM("no-transform"),
    ONLY_IF_CACHED("only-if-cached"),
    CACHE_EXTENSION("cache-extension"),
    NONE("")
    ;

    private final String directive;

    public static boolean contains(final String directive) {
        return Arrays.stream(values())
                .anyMatch(field -> StringUtils.startsWith(directive, field.getDirective()));
    }

    public static CacheDirective valueOfDirective(final String directive) {
        return Arrays.stream(values())
                .filter(value -> value.directive.equals(directive))
                .findFirst()
                .orElse(NONE);
    }
}
