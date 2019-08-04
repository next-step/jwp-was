package webserver.http.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * cache-response-directive =
 *       "public"                               ; Section 14.9.1
 *     | "private" [ "=" <"> 1#field-name <"> ] ; Section 14.9.1
 *     | "no-cache" [ "=" <"> 1#field-name <"> ]; Section 14.9.1
 *     | "no-store"                             ; Section 14.9.2
 *     | "no-transform"                         ; Section 14.9.5
 *     | "must-revalidate"                      ; Section 14.9.4
 *     | "proxy-revalidate"                     ; Section 14.9.4
 *     | "max-age" "=" delta-seconds            ; Section 14.9.3
 *     | "s-maxage" "=" delta-seconds           ; Section 14.9.3
 *     | cache-extension                        ; Section 14.9.6
 *
 * cache-extension = token [ "=" ( token | quoted-string ) ]
 */
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
