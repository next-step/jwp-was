package webserver.session;

public class HttpSessionIdHolder {

    private static final ThreadLocal<String> SESSION_ID = new ThreadLocal<>();

    private HttpSessionIdHolder() {
        throw new AssertionError();
    }

    public static void generate(String sessionId) {
        SESSION_ID.set(sessionId);
    }

    public static String getSessionId() {
        return SESSION_ID.get();
    }

    public static void invalidate() {
        SESSION_ID.remove();
    }

}
