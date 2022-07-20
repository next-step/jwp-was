package domain;

import java.util.List;

public class HttpRequest {
	private HttpRequestLine httpRequestLine;
	private HttpRequestHeaders httpRequestHeaders;

	public HttpRequest(List<String> lines) {
		httpRequestLine = HttpRequestLine.of(lines.get(0));
		httpRequestHeaders = HttpRequestHeaders.of(lines);
	}
}
