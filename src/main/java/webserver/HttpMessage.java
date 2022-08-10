package webserver;

import java.util.HashMap;

public class HttpMessage {
	private HashMap<String, String> headers;
	private HttpVersion httpVersion;

	public HttpMessage(HashMap<String, String> headers, HttpVersion httpVersion) {
		this.headers = headers;
		this.httpVersion = httpVersion;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public HttpVersion getHttpVersion() {
		return httpVersion;
	}
}
