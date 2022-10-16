package webserver.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class SessionManagerTest {
	@Test
	void removeSession() {
		String sessionId = getSessionId();
		SessionManager sessionManager = SessionManager.getInstance();
		sessionManager.createSession(sessionId);
		sessionManager.removeSession(sessionId);

		assertFalse(sessionManager.isSessionIdValid(sessionId));
	}
	@Test
	void createSession(){
		String sessionId = getSessionId();
		SessionManager sessionManager = SessionManager.getInstance();
		sessionManager.createSession(sessionId);

		assertTrue(sessionManager.isSessionIdValid(sessionId));
	}

	String getSessionId() {
		return UUID.randomUUID().toString();
	}
}