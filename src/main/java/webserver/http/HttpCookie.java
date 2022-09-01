package webserver.http;

import utils.ParamsUtils;

import java.util.Map;
import java.util.Objects;

public class HttpCookie {
	private final Map<String, String> cookies;

	public HttpCookie(String cookieHeader) {
		this(ParamsUtils.parsedCookie(cookieHeader));
	}

	public HttpCookie(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public String get(String name) {
		return cookies.get(name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HttpCookie that = (HttpCookie) o;
		return Objects.equals(cookies, that.cookies);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cookies);
	}

	@Override
	public String toString() {
		return "HttpCookie{" +
			"cookies=" + cookies +
			'}';
	}
}
