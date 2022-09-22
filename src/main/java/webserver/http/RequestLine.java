package webserver;

import java.net.URI;

public class RequestLine {
	private HttpMethod method;
	private URI uri;

	private String queryString;
	private HttpVersion httpVersion;
	public RequestLine(HttpMethod method, URI uri, HttpVersion httpVersion) {
		this.method = method;
		this.uri = uri;
		this.queryString = uri.getQuery();
		this.httpVersion = httpVersion;
	}
	public HttpMethod getMethod() {
		return method;
	}

	public URI getUri() {
		return uri;
	}

	public String getQueryString() {
		return queryString;
	}

	public HttpVersion getHttpVersion() {
		return httpVersion;
	}
}
