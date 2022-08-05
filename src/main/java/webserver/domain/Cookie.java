package webserver.domain;

import static webserver.servlet.UserListController.KEY_LOGINED;

public class Cookie {

    private String key;
    private String value;

    // 리팩토링 시 directives map 으로 확장 -> 요청/응답 쿠키 분리 (Cookie, SetCookie)
    private String path;

    public Cookie(String key, String value) {
        this(key, value, "");
    }

    public Cookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public static Cookie requestCookie(String cookieString) {
        String[] keyVal = cookieString.split("=");
        return new Cookie(keyVal[0], keyVal[1]);
    }

    public static Cookie empty() {
        return Cookie.requestCookie("");
    }

    public static Cookie loginedWithPath(String path) {
        return new Cookie(KEY_LOGINED, Boolean.TRUE.toString(), "Path=" + path);
    }

    public static Cookie notLoginedWithPath(String path) {
        return new Cookie(KEY_LOGINED, Boolean.FALSE.toString(), "Path=" + path);
    }

    public static Cookie jsseionId(String sessionId) {
        return new Cookie(HttpHeader.JSESSIONID, sessionId, "");
    }

    public boolean isSessionId(String id) {
        return this.key.equals(id);
    }

    public String string() {
        return key + "=" + value + "; " + path;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public String getPath() {
        return this.path;
    }

}
