package webserver.http.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import webserver.http.CacheDirective;

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
    CACHE_EXTENSION("cache-extension")
    ;

    private final String directive;
}
