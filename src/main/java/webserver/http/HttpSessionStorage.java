package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HttpSessionStorage {

	private static final String INVALID_SESSION_INFO = "유효하지 않은 세션 정보입니다.";

	private static final Map<String, HttpSession> storage = new HashMap<>();

	private HttpSessionStorage() {
	}

	public static HttpSessionStorage getInstance() {
		return new HttpSessionStorage();
	}

	public HttpSession getSession(String id) {
		return Optional.ofNullable(storage.get(id))
					   .orElse(new HttpSession());
	}

	public void save(String id, HttpSession httpSession) {
		if (Objects.isNull(httpSession)) {
			throw new IllegalArgumentException(INVALID_SESSION_INFO);
		}
		storage.put(id, httpSession);
	}
}
