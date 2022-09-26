package webserver.http;

import java.util.HashMap;
import java.util.UUID;

public class HttpSession {
	private UUID sessionId;
	private HashMap<String, Object> attributes;

	public HttpSession(UUID sessionId) {
		this.sessionId = sessionId;
		this.attributes = new HashMap<>();
	}

	public UUID getSessionId() {
		return this.sessionId;
	}

	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	public void removeAttribute(String key) {
		attributes.remove(key);
	}

	public void invalidate() {
		sessionId = null;
		attributes = null;
	}
}
