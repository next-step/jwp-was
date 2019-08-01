package webserver.request.enums;

import java.util.Arrays;

/**
 * Created by hspark on 2019-08-01.
 */
public enum HttpVersion {
	HTTP_1_1("HTTP/1.1");

	private String headerValue;

	HttpVersion(String headerValue) {
		this.headerValue = headerValue;
	}

	public static HttpVersion findByHeaderValue(String value) {
		return Arrays.stream(HttpVersion.values())
			.filter(it -> it.equalsHeaderValue(value))
			.findAny()
			.orElseThrow(() -> new IllegalArgumentException("Not Found HttpVerison"));
	}

	public boolean equalsHeaderValue(String headerValue) {
		return this.headerValue == headerValue;
	}
}
