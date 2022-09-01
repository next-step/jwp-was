package webserver.http;

import java.util.Objects;

public class HttpRequest {
	private RequestLine requestLine;
	private HttpHeaders httpHeaders;
	private RequestParams requestParams;

	public HttpRequest(RequestLine requestLine, HttpHeaders httpHeaders, RequestParams requestParams) {
		this.requestLine = requestLine;
		this.httpHeaders = httpHeaders;
		this.requestParams = requestParams;
	}

	public String getPath() {
		return requestLine.path();
	}
	public HttpMethod getMethod() {
		return requestLine.getMethod();
	}
	public String getParameter(String name) {
		return requestParams.value(name);
	}
	public HttpCookie getCookie() {
		return httpHeaders.getCookie();
	}
	public String getHeader(String name) {
		return httpHeaders.get(name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HttpRequest that = (HttpRequest) o;
		return Objects.equals(requestLine, that.requestLine) && Objects.equals(httpHeaders, that.httpHeaders) && Objects.equals(requestParams, that.requestParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(requestLine, httpHeaders, requestParams);
	}

	@Override
	public String toString() {
		return "HttpRequest{" +
			"requestLine=" + requestLine +
			", httpHeaders=" + httpHeaders +
			", requestParams=" + requestParams +
			'}';
	}
}
