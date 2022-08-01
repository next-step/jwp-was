package webserver.http.request;

import java.util.Objects;
import java.util.Optional;

import webserver.http.HttpMethod;
import webserver.http.HttpSession;
import webserver.http.HttpSessionStorage;

public class HttpRequest {
	private final HttpRequestLine httpRequestLine;
	private final HttpRequestHeaders httpRequestHeaders;
	private final HttpRequestBody httpRequestBody;
	private final HttpSession httpSession;

	public HttpRequest(HttpRequestLine httpRequestLine, HttpRequestHeaders httpRequestHeaders, HttpRequestBody httpRequestBody) {
		this.httpRequestLine = httpRequestLine;
		this.httpRequestHeaders = httpRequestHeaders;
		this.httpRequestBody = httpRequestBody;
		this.httpSession = HttpSessionStorage.getInstance().getSession(httpRequestHeaders.getCookies());
	}

	public String getHeader(String attribute) {
		return httpRequestHeaders.getAttribute(attribute);
	}

	public String getParameter(String attribute) {
		return Optional.ofNullable(httpRequestLine.getParameters().getValue(attribute))
					   .orElse(httpRequestBody.getAttribute(attribute));
	}

	public HttpRequestHeaders getHeaders() {
		return httpRequestHeaders;
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

	public HttpRequestBody getHttpBody() {
		return httpRequestBody;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HttpRequest that = (HttpRequest) o;
		return Objects.equals(httpRequestLine, that.httpRequestLine) && Objects.equals(httpRequestHeaders, that.httpRequestHeaders)
			   && Objects.equals(httpRequestBody, that.httpRequestBody);
	}

	@Override
	public int hashCode() {
		return Objects.hash(httpRequestLine, httpRequestHeaders, httpRequestBody);
	}
}
