package webserver.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import webserver.http.CacheDirective;

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
    CACHE_EXTENSION("cache-extension")
    ;

    private final String directive;
}
