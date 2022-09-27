package webserver.http;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class DefaultHttpRequest implements HttpRequest {
	private static final String SESSION_ID = "JSESSIONID";
	private HttpMethod method;
	private URI uri;
	private String path;
	private MultiValueMap<String, String> queryParams;
	private MultiValueMap<String, String> headers;
	private Map<String, Object> attributes;
	private HttpVersion httpVersion;
	private HttpSession httpSession;
	private UUID sessionId;

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
	public String getParameter(String name) {
		if (CollectionUtils.isEmpty(queryParams)) {
			this.queryParams = QueryStringDecoder.parseQueryString(uri.getQuery());
		}
		return queryParams.getFirst(name);
	}

	@Override
	public MultiValueMap<String, String> getHeaders() {
		return this.headers;
	}

	@Override
	public HttpVersion getHttpVersion() {
		return this.httpVersion;
	}

	@Override
	public void setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
	}

	@Override
	public HttpMethod getMethod() {
		return this.method;
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
	public URI getURI() {
		return this.uri;
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (attributes == null) {
			attributes = new HashMap<>();
		}
		attributes.put(name, value);
	}

	@Override
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	@Override
	public void setSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public HttpSession getSession() {
		return this.httpSession;
	}

	@Override
	public UUID getCookieSessionId() {
		if (Objects.nonNull(sessionId)) {
			return sessionId;
		}
		return generateSessionId();
	}

	private UUID generateSessionId() {
		String cookie = getHeader("Cookie");
		if (cookie!= null) {
			String[] cookieTokens = cookie.split(";");
			for (String cookieToken : cookieTokens) {
				String[] keyValueToken = cookieToken.split("=");
				if (keyValueToken[0].equals(SESSION_ID)) {
					sessionId = UUID.fromString(keyValueToken[1]);
					return sessionId;
				}
			}
		}
		return UUID.randomUUID();
	}
}
