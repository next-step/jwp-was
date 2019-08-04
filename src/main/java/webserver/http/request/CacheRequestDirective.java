package webserver.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * cache-request-directive =
 *        "no-cache"                          ; Section 14.9.1
 *      | "no-store"                          ; Section 14.9.2
 *      | "max-age" "=" delta-seconds         ; Section 14.9.3, 14.9.4
 *      | "max-stale" [ "=" delta-seconds ]   ; Section 14.9.3
 *      | "min-fresh" "=" delta-seconds       ; Section 14.9.3
 *      | "no-transform"                      ; Section 14.9.5
 *      | "only-if-cached"                    ; Section 14.9.4
 *      | cache-extension                     ; Section 14.9.6
 *
 * cache-extension = token [ "=" ( token | quoted-string ) ]
 */
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
