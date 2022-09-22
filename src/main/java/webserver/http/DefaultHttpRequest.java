package webserver.http;

import java.net.URI;

import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

public class DefaultHttpRequest implements HttpRequest {
	private HttpMethod method;
	private URI uri;
	private String path;
	private MultiValueMap<String, String> queryParams;
	private MultiValueMap<String, String> headers;
	private HttpVersion httpVersion;

	public DefaultHttpRequest(RequestLine requestLine, MultiValueMap<String, String> headers) {
		this.method = requestLine.getMethod();
		this.uri = requestLine.getUri();
		this.path = uri.getPath();
		this.headers = headers;
		this.httpVersion = requestLine.getHttpVersion();
	}

	@Override
	public MultiValueMap<String, String> getQueryParams() {
		if (CollectionUtils.isEmpty(queryParams)) {
			this.queryParams = QueryStringDecoder.parseQueryString(uri.getQuery());
		}
		return this.queryParams;
	}

	@Override
	public MultiValueMap<String, String> getHeaders() {
		return this.headers;
	}

	@Override
	public HttpVersion getProtocol() {
		return this.httpVersion;
	}

	@Override
	public HttpMethod getMethod() {
		return this.method;
	}

	@Override
	public String getParameter(String name) {
		if (CollectionUtils.isEmpty(queryParams)) {
			this.queryParams = QueryStringDecoder.parseQueryString(uri.getQuery());
		}
		return queryParams.getFirst(name);
	}

	@Override
	public String getHeader(String key) {
		return headers.getFirst(key);
	}
	@Override
	public URI getURI() {
		return this.uri;
	}
}
