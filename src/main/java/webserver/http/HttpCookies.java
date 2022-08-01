package webserver.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HttpCookies {
	private static final int MIN_LENGTH_COOKIE = 2;
	private static final int INDEX_KEY = 0;
	private static final int INDEX_VALUE = 1;
	private static final String EQUAL = "=";
	private static final String SEMICOLON = ";";
	private static final String NEW_LINE = "\r\n";

	private final List<HttpCookie> httpCookies = new ArrayList<>();

	public HttpCookies() {
	}

	public HttpCookies(String stringCookies) {
		if (Objects.isNull(stringCookies)) {
			return;
		}
		String[] flatCookie = stringCookies.split(SEMICOLON);
		if (flatCookie.length > 1) {
			for (String cookie : flatCookie) {
				String[] data = cookie.split(EQUAL);
				if (data.length == MIN_LENGTH_COOKIE) {
					httpCookies.add(new HttpCookie(data[INDEX_KEY].trim(), data[INDEX_VALUE].trim()));
				}
			}
		}
	}

	public HttpCookie find(String name) {
		return httpCookies.stream()
						  .filter(c -> c.getName().equals(name))
						  .findFirst()
						  .orElse(null);
	}

	public void addCookie(String name, String value, String path) {
		httpCookies.add(new HttpCookie(name, value, path));
	}

	public void addCookie(HttpCookie httpCookie) {
		httpCookies.add(httpCookie);
	}

	public List<HttpCookie> getCookies() {
		return httpCookies;
	}

	public void write(OutputStream outputStream) throws IOException {
		StringBuilder builder = new StringBuilder();
		for(HttpCookie cookie : httpCookies) {
			builder.append(cookie.toResponseString());
			builder.append(NEW_LINE);
		}
		outputStream.write(builder.toString().getBytes(StandardCharsets.UTF_8));
	}
}
