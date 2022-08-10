package webserver.http;

import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpSession.SESSION_ID_NAME;

public class HttpCookie {
    Map<String, String> cookies = new HashMap<>();

    public HttpCookie(String cookieString) {
        if (cookieString != null) {
            cookieParser(cookieString);
        }
    }

    public void cookieParser(String cookieString) {
        for (String cookie : cookieString.split(";")) {
            String cookieName = cookie.split("=")[0];
            String cookieValue = cookie.split("=")[1];
            cookies.put(cookieName, cookieValue);
        }
    }

    public String getSessionId() {
        return cookies.get(SESSION_ID_NAME);
    }
}
