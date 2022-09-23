package webserver.http;

import org.springframework.util.MultiValueMap;

public interface HttpMessage {
	void addHeader(String key, String value);

	String getHeader(String key);

	void setHttpVersion(HttpVersion httpVersion);

	MultiValueMap<String, String> getHeaders();

	HttpVersion getHttpVersion();
}
