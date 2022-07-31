package constant;

public enum HttpHeader {

     ACCEPT("Accept"),
     ACCEPT_CHARSET("Accept-Charset"),
     ACCEPT_ENCODING("Accept-Encoding"),
     ACCEPT_LANGUAGE("Accept-Language"),
     ACCEPT_PATCH("Accept-Patch"),
     ACCEPT_RANGES("Accept-Ranges"),
     ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials"),
     ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers"),
     ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods"),
     ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
     ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers"),
     ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age"),
     ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers"),
     ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"),
     AGE("Age"),
     ALLOW("Allow"),
     AUTHORIZATION("Authorization"),
     CACHE_CONTROL("Cache-Control"),
     CONNECTION("Connection"),
     CONTENT_ENCODING("Content-Encoding"),
     CONTENT_DISPOSITION("Content-Disposition"),
     CONTENT_LANGUAGE("Content-Language"),
     CONTENT_LENGTH("Content-Length"),
     CONTENT_LOCATION("Content-Location"),
     CONTENT_RANGE("Content-Range"),
     CONTENT_TYPE("Content-Type"),
     COOKIE("Cookie"),
     DATE("Date"),
     ETAG("ETag"),
     EXPECT("Expect"),
     EXPIRES("Expires"),
     FROM("From"),
     HOST("Host"),
     IF_MATCH("If-Match"),
     IF_MODIFIED_SINCE("If-Modified-Since"),
     IF_NONE_MATCH("If-None-Match"),
     IF_RANGE("If-Range"),
     IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
     LAST_MODIFIED("Last-Modified"),
     LINK("Link"),
     LOCATION("Location"),
     MAX_FORWARDS("Max-Forwards"),
     ORIGIN("Origin"),
     PRAGMA("Pragma"),
     PROXY_AUTHENTICATE("Proxy-Authenticate"),
     PROXY_AUTHORIZATION("Proxy-Authorization"),
     RANGE("Range"),
     REFERER("Referer"),
     RETRY_AFTER("Retry-After"),
     SERVER("Server"),
     SET_COOKIE("Set-Cookie"),
     SET_COOKIE2("Set-Cookie2"),
     TE("TE"),
     TRAILER("Trailer"),
     TRANSFER_ENCODING("Transfer-Encoding"),
     UPGRADE("Upgrade"),
     USER_AGENT("User-Agent"),
     VARY("Vary"),
     VIA("Via"),
     WARNING("Warning"),
     WWW_AUTHENTICATE("WWW-Authenticate");

    private String value;

    HttpHeader(String value) {
        this.value = value;
    }

     public String getValue() {
          return value;
     }
}
