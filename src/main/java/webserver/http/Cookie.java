package webserver.http;

import java.util.Objects;

public class Cookie {
	private static final String DEFAULT_COOKIE_PATH = "/";
	private String cookieName;
	private String cookieValue;
	private String cookiePath = DEFAULT_COOKIE_PATH;
	private String cookieDomain;
	private int cookieMaxAge;

	public Cookie(String cookieName, String cookieValue) {
		this.cookieName = cookieName;
		this.cookieValue = cookieValue;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	public void setCookieMaxAge(int cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}

	public String getCookieName() {
		return cookieName;
	}

	public String getCookieValue() {
		return cookieValue;
	}

	public String toEncoded() {
		StringBuilder builder = new StringBuilder();
		builder.append(cookieName)
			.append("=")
			.append(cookieValue);
		if (cookiePath != null) {
			builder.append("; Path=")
				.append(cookiePath);
		}
		if (cookieDomain != null) {
			builder.append("; Domain=")
				.append(cookieDomain);
		}
		if (cookieMaxAge > 0) {
			builder.append("; Max-Age=")
				.append(cookieMaxAge);
		}
		return builder.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Cookie cookie = (Cookie)o;
		return Objects.equals(cookieName, cookie.cookieName) && Objects.equals(cookieValue,
			cookie.cookieValue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cookieName, cookieValue);
	}
}
