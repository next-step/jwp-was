package webserver;

import java.net.URI;

import org.springframework.util.MultiValueMap;

public interface HttpRequest extends HttpMessage {
	public MultiValueMap<String, String> getQueryParams();
	public HttpMethod getMethod();
	public URI getURI();
}
