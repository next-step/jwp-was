package webserver.http.request;

import java.util.List;

import webserver.http.HttpMethod;

public class HttpRequest {
	private HttpRequestLine httpRequestLine;
	private HttpRequestHeaders httpRequestHeaders;

	public HttpRequest(List<String> lines) {
		httpRequestLine = HttpRequestLine.of(lines.get(0));
		httpRequestHeaders = HttpRequestHeaders.of(lines.subList(1, lines.size()));
	}

	public String getPath() {
		return httpRequestLine.getPath();
	}

	public HttpMethod getMethod() {
		return httpRequestLine.getMethod();
	}

	public HttpProtocol getProtocol() {
		return httpRequestLine.getProtocol();
	}
}
