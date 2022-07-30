package webserver.http;

public final class SessionConfig {

    private static final String DEFAULT_SESSION_COOKIE_NAME = "JSESSIONID";

    private SessionConfig() {
        throw new AssertionError("'SessionConfig' can not be instanced");
    }

    public static String sessionCookieName() {
        return DEFAULT_SESSION_COOKIE_NAME;
    }
}
