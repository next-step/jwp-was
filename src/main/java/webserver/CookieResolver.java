package webserver;

import java.util.Map;

import static java.util.Optional.ofNullable;

public class CookieResolver {

    public String getContentType(Map<String, String> cookies) {
        return ofNullable(cookies.get("Accept"))
                .map(accepts -> accepts.split(",")[0])
                .orElse("text/html");
    }

}
