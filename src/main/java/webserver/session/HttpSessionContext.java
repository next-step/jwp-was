package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class HttpSessionContext {

    private static final Map<String, HttpSession> SESSION_CONTEXT = new HashMap<>();

    private HttpSessionContext() {
        throw new AssertionError();
    }

    public static void add(final HttpSession session) {
        SESSION_CONTEXT.put(session.getId(), session);
    }

    public static HttpSession get(final String id) {
        return SESSION_CONTEXT.get(id);
    }

}
