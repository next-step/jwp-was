package webserver;

import java.util.HashMap;

public class HttpRequest extends HttpMessage {
	private RequestLine requestLine;

	public HttpRequest(RequestLine requestLine, HashMap<String, String> headers, HttpVersion httpVersion) {
		super(headers, httpVersion);
		this.requestLine = requestLine;
	}
}
