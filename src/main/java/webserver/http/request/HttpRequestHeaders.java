package webserver.http.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import webserver.http.HttpCookies;

public class HttpRequestHeaders {
	protected static final String HEADER_DELIMITER = ":";
	protected static final int INDEX_KEY = 0;
	protected static final int INDEX_VALUE = 1;
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String COOKIE = "Cookie";

	private final Map<String, String> headers;

	private HttpRequestHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public static HttpRequestHeaders of(List<String> lines) {
		Map<String, String> headers = new HashMap<>();
		for (String line : lines) {
			String[] tokens = line.split(HEADER_DELIMITER);
			headers.put(tokens[INDEX_KEY].trim(), tokens[INDEX_VALUE].trim());
		}
		return new HttpRequestHeaders(headers);
	}

	public static HttpRequestHeaders of(Map<String, String> headers) {
		return new HttpRequestHeaders(headers);
	}

	public Boolean hasContentLength() {
		return headers.containsKey(CONTENT_LENGTH);
	}

	public String getAttribute(String attribute) {
		return headers.get(attribute);
	}

	public int getContentLength() {
		return Integer.parseInt(headers.get(CONTENT_LENGTH));
	}

	public HttpCookies getCookies() {
		return HttpCookies.of(headers.get(COOKIE));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HttpRequestHeaders that = (HttpRequestHeaders) o;
		return Objects.equals(headers, that.headers);
	}

	@Override
	public int hashCode() {
		return Objects.hash(headers);
	}
}
