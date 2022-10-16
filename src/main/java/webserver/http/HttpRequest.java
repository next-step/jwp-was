package webserver.http;

import java.util.Map;

import org.springframework.util.MultiValueMap;

public interface HttpRequest extends HttpMessage {
	MultiValueMap<String, String> getQueryParams();

	HttpMethod getMethod();

	String getParameter(String name);

	String getRequestUri();

	String getServletPath();
	String getQueryString();

	void setAttribute(String name, Object value);

	Object getAttribute(String name);

	Map<String, Cookie> getCookies();

	HttpSession getSession();

	String getRequestedSessionId();
}
