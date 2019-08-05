package webserver.http.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import webserver.http.CacheDirective;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CacheResponseDirective implements CacheDirective {

    PUBLIC("public"),
    PRIVATE("private"),
    NO_CACHE("no-cache"),
    NO_STORE("no-store"),
    NO_TRANSFORM("no-transform"),
    MUST_REVALIDATE("must-revalidate"),
    PROXY_REVALIDATE("proxy-revalidate"),
    MAX_AGE("max-age"),
    S_MAXAGE("s-maxage"),
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
