package webserver;

public class RequestLine {
	private String method;
	private Uri uri;
	private HttpVersion httpVersion;
	public RequestLine(String requestMethod, Uri uri, HttpVersion httpVersion) {
		this.method = requestMethod;
		this.uri = uri;
		this.httpVersion = httpVersion;
	}

	public String getMethod() {
		return method;
	}

	public Uri getUri() {
		return uri;
	}

	public HttpVersion getHttpVersion() {
		return httpVersion;
	}
}
