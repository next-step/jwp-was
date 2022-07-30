package webserver.http;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpCookies {
	private static final int MIN_LENGTH_COOKIE = 2;
	private static final int INDEX_KEY = 0;
	private static final int INDEX_VALUE = 1;
	private static final String EQUAL = "=";
	private static final String SEMICOLON = ";";
	private static final String BLANK = " ";

	private final Map<String, String> httpCookies;

	private HttpCookies(Map<String, String> httpCookies) {
		this.httpCookies = httpCookies;
	}

	public static HttpCookies of(String stringCookies) {
		if (Objects.isNull(stringCookies) || stringCookies.isEmpty()) {
			return new HttpCookies(new HashMap<>());
		}
		Map<String, String> cookieMap = new LinkedHashMap<>();
		String[] flatCookie = stringCookies.split(SEMICOLON);
		for (String cookie : flatCookie) {
			inputCookie(cookieMap, cookie);
		}
		return new HttpCookies(cookieMap);
	}

	private static void inputCookie(Map<String, String> cookieMap, String cookie) {
		String[] data = cookie.split(EQUAL);
		if (data.length == MIN_LENGTH_COOKIE) {
			cookieMap.put(data[INDEX_KEY].trim(), data[INDEX_VALUE].trim());
			return;
		}
		cookieMap.put(data[INDEX_KEY].trim(), "");
	}

	public String getFlatCookies() {
		return httpCookies.entrySet().stream()
						   .map(key -> key.getKey() + EQUAL + key.getValue())
						   .collect(Collectors.joining(SEMICOLON + BLANK));
	}

	public String getCookie(String key) {
		return httpCookies.get(key);
	}
}
