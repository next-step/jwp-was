package webserver.http;

import webserver.HttpCookie;
import webserver.HttpHeaders;
import webserver.HttpMethod;
import webserver.RequestLine;

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

}
