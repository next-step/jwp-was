package webserver.http;

import java.net.URI;
import java.nio.ByteBuffer;

import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class DefaultFormHttpRequest implements HttpRequest {
	private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
	private HttpMethod method;
	private URI uri;
	private String path;
	private MultiValueMap<String, String> queryParams;
	private MultiValueMap<String, String> headers;
	private HttpVersion httpVersion;
	private ByteBuffer content;

	public DefaultFormHttpRequest(RequestLine requestLine, MultiValueMap<String, String> headers, ByteBuffer content) {
		this.method = requestLine.getMethod();
		this.uri = requestLine.getUri();
		this.path = uri.getPath();
		this.headers = headers;
		this.httpVersion = requestLine.getHttpVersion();
		this.content = content;
	}

	public boolean isFormRequest() {
		return headers.containsKey("Content-Type") && headers.getFirst("Content-Type")
			.equals(FORM_CONTENT_TYPE);
	}

	@Override
	public MultiValueMap<String, String> getQueryParams() {
		if (CollectionUtils.isEmpty(queryParams) && isFormRequest()) {
			return QueryStringDecoder.parseQueryString(content);
		}
		return this.queryParams;
	}

	@Override
	public String getParameter(String name) {
		if (CollectionUtils.isEmpty(queryParams) && isFormRequest()) {
			this.queryParams = QueryStringDecoder.parseQueryString(content);
		}
		return queryParams.getFirst(name);
	}

	@Override
	public void addHeader(String key, String value) {
		if (headers == null) {
			headers = new LinkedMultiValueMap<>();
		}
		headers.add(key, value);
	}

	@Override
	public String getHeader(String key) {
		return headers.getFirst(key);
	}

	@Override
	public MultiValueMap<String, String> getHeaders() {
		return this.headers;
	}

	@Override
	public void setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
	}

	@Override
	public HttpVersion getHttpVersion() {
		return this.httpVersion;
	}

	@Override
	public HttpMethod getMethod() {
		return this.method;
	}

	@Override
	public URI getURI() {
		return this.uri;
	}
}
