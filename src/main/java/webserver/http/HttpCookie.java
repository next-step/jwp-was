package webserver.http;

import utils.ParamsUtils;

import java.util.Map;

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
}
