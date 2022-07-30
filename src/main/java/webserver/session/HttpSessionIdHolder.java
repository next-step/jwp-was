package webserver.session;

public class HttpSessionIdHolder {

    private final ThreadLocal<String> sessionId = new ThreadLocal<>();
    private final SessionIdGenerator sessionIdGenerator;

    public HttpSessionIdHolder(final SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }

    public void generate(String sessionId) {
        if (HttpSessionContext.has(sessionId)) {
            this.sessionId.set(sessionId);
        }
        createSession();
    }

    private void createSession() {
        if (sessionId.get() == null) {
            final HttpSession httpSession = new HttpSession(sessionIdGenerator);
            HttpSessionContext.add(httpSession);
            this.sessionId.set(httpSession.getId());
        }
    }

    public String getSessionId() {
        return sessionId.get();
    }

    public void invalidate() {
        sessionId.remove();
    }

}
