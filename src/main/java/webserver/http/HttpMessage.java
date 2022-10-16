package webserver.http;

import org.springframework.util.MultiValueMap;

public interface HttpMessage {
	String CONTENT_TYPE = "Content-Type";
	String CONTENT_LENGTH = "Content-Length";
	String SET_COOKIE = "Set-Cookie";
	String LOCATION = "Location";

	void addHeader(String key, String value);

	String getHeader(String key);

	void setHttpVersion(HttpVersion httpVersion);

	MultiValueMap<String, String> getHeaders();

	HttpVersion getHttpVersion();
}
