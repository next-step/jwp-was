package webserver.http;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
	private static final SessionManager instance = new SessionManager();
	private Map<UUID, HttpSession> sessions = new ConcurrentHashMap<>();

	public static SessionManager getInstance() {
		return instance;
	}

	public HttpSession findSession(UUID sessionId) {
		return sessions.get(sessionId);
	}

	public void removeSession(UUID sessionId) {
		HttpSession session = sessions.get(sessionId);
		session.invalidate();
		sessions.remove(sessionId);
	}

	public boolean isSessionIdValid(UUID sessionId) {
		if (sessionId == null) {
			return false;
		}
		return sessions.containsKey(sessionId);
	}

	public void createSession(UUID sessionId) {
		HttpSession session = new HttpSession(sessionId);
		sessions.put(sessionId, session);
	}
}
