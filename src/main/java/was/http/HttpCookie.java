package was.http;

import com.google.common.base.Strings;
import utils.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpCookie {
    private final Map<String, String> cookies;

    HttpCookie() {
        this(new HashMap<>());
    }

    HttpCookie(String cookieValue) {
        this(HttpRequestUtils.parseCookies(cookieValue));
    }

    HttpCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

    public boolean isRequestedSessionIdFromCookie() {
        String sessionValue = cookies.get(HttpSessionStorage.SESSION_ID_NAME);
        return !Strings.isNullOrEmpty(sessionValue);
    }
}
