package cookie;

public class Cookie {
    private static String cookie;

    public static String getResponseCookie() {
        return "Set-Cookie: " + cookie + "; Path=/";
    }

    public static String getRequestCookie() {
        return "Cookie: " + cookie + ";";
    }

    public static boolean exists() {
        return cookie != null && !cookie.isBlank();
    }

    public void setResponseLoginCookie(boolean loginSuccess) {
        if (loginSuccess) {
            cookie = "logined=true";
            return;
        }
        cookie = "logined=false";
    }

    public String getCookie() {
        return cookie;
    }
}
