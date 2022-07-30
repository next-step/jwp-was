package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaderParser {
    static String isLogin(String cookieString) {
        if (cookieString.isEmpty()) {
            return "false";
        }
        Map<String, String> cookies = new HashMap<>();
        for (String cookie : cookieString.split(";")) {
            String cookieName = cookie.split("=")[0];
            String cookieValue = cookie.split("=")[1];
            cookies.put(cookieName, cookieValue);
        }
        String loginedValue = cookies.get("logined");
        if (loginedValue == null) {
            return "false";
        }
        return loginedValue;
    }
}
