package webserver.http;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {
	public static final String SESSION_ID = "JSESSIONID";
	private String sessionId;
	private volatile boolean changed;
	private final Map<String, Object> attributes = new ConcurrentHashMap<>();

	public HttpSession(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
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
	}

	public boolean setChanged(boolean changed) {
		this.changed = changed;
		return true;
	}

	public boolean hasChanged() {
		return changed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		HttpSession that = (HttpSession)o;
		return Objects.equals(sessionId, that.sessionId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sessionId);
	}
}
