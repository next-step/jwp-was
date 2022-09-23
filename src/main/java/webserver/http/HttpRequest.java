package webserver.http;

import java.net.URI;

import org.springframework.util.MultiValueMap;

public interface HttpRequest extends HttpMessage {
	public MultiValueMap<String, String> getQueryParams();

	public HttpMethod getMethod();

	String getParameter(String name);

	public URI getURI();
}
