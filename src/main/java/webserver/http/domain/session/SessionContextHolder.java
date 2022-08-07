package webserver.http.domain.session;

public class SessionContextHolder {
    private static final ThreadLocal<Session> CONTEXT_HOLDER = new ThreadLocal<>();

    private SessionContextHolder() {
    }

    public static Session getCurrentSession() {
        return CONTEXT_HOLDER.get();
    }

    public static void saveCurrentSession(Session session) {
        CONTEXT_HOLDER.set(session);
    }

    public static void removeCurrentSession() {
        CONTEXT_HOLDER.remove();
    }

}
