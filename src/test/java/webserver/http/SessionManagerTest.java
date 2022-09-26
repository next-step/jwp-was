package webserver.http;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class SessionManagerTest {
	@Test
	void removeSession() {
		UUID sessionId = UUID.randomUUID();
		SessionManager sessionManager = SessionManager.getInstance();
		sessionManager.createSession(sessionId);
		sessionManager.removeSession(sessionId);
		assertFalse(sessionManager.isSessionIdValid(sessionId));
	}
	@Test
	void createSession(){
		UUID sessionId = UUID.randomUUID();
		SessionManager sessionManager = SessionManager.getInstance();
		sessionManager.createSession(sessionId);
		assertTrue(sessionManager.isSessionIdValid(sessionId));
	}
}