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

    public void setCookie(String cookie) {
        Cookie.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }
}
