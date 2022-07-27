package webserver.http.request;

import java.util.Objects;
import java.util.Optional;

import webserver.http.HttpMethod;

public class HttpRequest {
	private HttpRequestLine httpRequestLine;
	private HttpRequestHeaders httpRequestHeaders;
	private HttpRequestBody httpRequestBody;

	public HttpRequest(HttpRequestLine httpRequestLine, HttpRequestHeaders httpRequestHeaders, HttpRequestBody httpRequestBody) {
		this.httpRequestLine = httpRequestLine;
		this.httpRequestHeaders = httpRequestHeaders;
		this.httpRequestBody = httpRequestBody;
	}

	public boolean isLogin() {
		return httpRequestHeaders.isLogin();
	}

	public String getHeader(String attribute) {
		return httpRequestHeaders.getAttribute(attribute);
	}

	public String getParameter(String attribute) {
		return Optional.ofNullable(httpRequestLine.getParameters().getValue(attribute))
					   .orElse(httpRequestBody.getAttribute(attribute));
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
