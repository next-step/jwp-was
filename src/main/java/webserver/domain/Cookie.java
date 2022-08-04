package webserver.domain;

import static webserver.servlet.UserListController.KEY_LOGINED;

public class Cookie {

    private final String cookieString;

    public Cookie(String cookieString) {
        this.cookieString = cookieString;
    }

    public static Cookie loginedWithPath(String path) {
        return new Cookie(KEY_LOGINED + "=" + true + "; " + "Path=" + path);
    }

    public static Cookie notLoginedWithPath(String path) {
        return new Cookie(KEY_LOGINED + "=" + false + "; " + "Path=" + path);
    }

    public static Cookie jsseionId(String sessionId) {
        return new Cookie(HttpHeader.JSESSIONID + "=" + sessionId);
    }

    public boolean startsWith(String id) {
        return this.cookieString.startsWith(id);
    }

    public String[] split(String regex) {
        return this.cookieString.split(regex);
    }

    public String string() {
        return this.cookieString;
    }

}
