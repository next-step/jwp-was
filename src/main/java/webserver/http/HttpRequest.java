package webserver.http;

import java.net.URI;
import java.util.UUID;

import org.springframework.util.MultiValueMap;

public interface HttpRequest extends HttpMessage {
	MultiValueMap<String, String> getQueryParams();

	HttpMethod getMethod();

	String getParameter(String name);

	URI getURI();

	void setAttribute(String name, Object value);

	Object getAttribute(String name);

	void setSession(HttpSession httpSession);
	HttpSession getSession();
	UUID getCookieSessionId();
}
