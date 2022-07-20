package domain;

import java.util.List;

public class HttpRequestHeaders {
	private List<String> headers;

	private HttpRequestHeaders(List<String> headers) {
		this.headers = headers;
	}

	public static HttpRequestHeaders of(List<String> lines) {
		return new HttpRequestHeaders(lines);
	}

	public List<String> getHeaders() {
		return headers;
	}
}
