package webserver;

import org.springframework.util.MultiValueMap;

public interface HttpMessage {
	MultiValueMap<String, String> getHeaders();
	HttpVersion getHttpVersion();
}
