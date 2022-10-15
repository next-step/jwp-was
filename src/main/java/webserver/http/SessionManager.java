package webserver.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
	private static final SessionManager instance = new SessionManager();

	private SessionManager() {
	}

	private final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();

	public static SessionManager getInstance() {
		return instance;
	}

	public HttpSession findSession(String sessionId) {
		return sessions.get(sessionId);
	}

	public void removeSession(String sessionId) {
		HttpSession session = sessions.get(sessionId);
		session.invalidate();
		sessions.remove(sessionId);
	}

	public boolean isSessionIdValid(String sessionId) {
		if (sessionId == null) {
			return true;
		}
		return sessions.containsKey(sessionId);
	}

	public HttpSession createSession(String sessionId) {
		HttpSession session = new HttpSession(sessionId);
		sessions.put(sessionId, session);
		return session;
	}
}
