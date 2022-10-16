package webserver.http;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.common.base.Splitter;

public class DefaultRequest implements HttpRequest {
	private static final String SESSION_ID = "JSESSIONID";
	private static final String COOKIE = "Cookie";
	private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
	private HttpMethod method;
	private URI uri;
	private String requestUri;
	private String servletPath;
	private String queryString;
	private MultiValueMap<String, String> queryParams;
	private MultiValueMap<String, String> headers;
	private Map<String, Object> attributes;
	private Map<String, Cookie> cookies;
	private HttpVersion httpVersion;
	private ByteBuffer inputChannel;

	public DefaultRequest(RequestLine requestLine, MultiValueMap<String, String> headers, ByteBuffer inputChannel) {
		this.method = requestLine.getMethod();
		this.uri = requestLine.getUri();
		this.headers = headers;
		this.httpVersion = requestLine.getHttpVersion();
		this.inputChannel = inputChannel;
	}

	public boolean isFormRequest() {
		if(headers.containsKey(HttpMessage.CONTENT_TYPE)) {
			return getHeaders().get(HttpMessage.CONTENT_TYPE).stream()
				.anyMatch(contentType -> contentType.contains(FORM_CONTENT_TYPE));
		}
		return false;
	}

	@Override
	public MultiValueMap<String, String> getQueryParams() {
		if (CollectionUtils.isEmpty(queryParams)) {
			if (isFormRequest()) {
				return QueryStringDecoder.parseQueryString(inputChannel);
			}
			return QueryStringDecoder.parseQueryString(uri.getQuery());
		}
		return this.queryParams;
	}

	@Override
	public String getParameter(String name) {
		if (CollectionUtils.isEmpty(queryParams)) {
			this.queryParams = getQueryParams();
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
	public MultiValueMap<String, String> getHeaders() {
		return this.headers;
	}

	@Override
	public String getHeader(String key) {
		return headers.getFirst(key);
	}

	public String getServletPath() {
		if (Objects.isNull(servletPath)) {
			servletPath = getRequestUri();
			if (servletPath.contains(".")) {
				int index = servletPath.lastIndexOf("/");
				servletPath = servletPath.substring(0, index);
			}
		}
		return servletPath;
	}

	@Override
	public String getQueryString() {
		if (Objects.isNull(queryString)) {
			this.queryString = uri.getQuery();
		}
		return queryString;
	}

	@Override
	public String getRequestUri() {
		if (Objects.isNull(requestUri)) {
			requestUri = uri.getPath();
		}
		return requestUri;
	}

	@Override
	public HttpVersion getHttpVersion() {
		return httpVersion;
	}

	@Override
	public HttpMethod getMethod() {
		return method;
	}

	@Override
	public void setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
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
	public HttpSession getSession() {
		SessionManager sessionManager = SessionManager.getInstance();
		String sessionId = getRequestedSessionId();

		if (sessionManager.isSessionIdValid(sessionId)) {
			return sessionManager.findSession(sessionId);
		}
		HttpSession session = sessionManager.createSession(sessionId);
		session.setChanged(true);
		return session;
	}

	public Map<String, Cookie> getCookies() {
		if (!CollectionUtils.isEmpty(cookies)) {
			return cookies;
		}
		String cookieHeader = getHeader(COOKIE);
		if (cookieHeader != null) {
			cookies = initializeCookie(cookieHeader);
		}
		return cookies;
	}

	@Override
	public String getRequestedSessionId() {
		cookies = getCookies();
		if (!CollectionUtils.isEmpty(cookies) && cookies.containsKey(SESSION_ID)) {
			return cookies.get(SESSION_ID).getCookieValue();
		}
		return UUID.randomUUID().toString();
	}

	private Map<String, Cookie> initializeCookie(String cookieHeader) {
		return Splitter.on(";")
			.omitEmptyStrings()
			.trimResults()
			.withKeyValueSeparator("=")
			.split(cookieHeader)
			.entrySet()
			.stream()
			.collect(Collectors.toMap(Map.Entry::getKey, entry -> new Cookie(entry.getKey(), entry.getValue())));
	}
}
