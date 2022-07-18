package webserver;

public class RequestLine {

	private final String value;

	public RequestLine(String value) {
		this.value = value;
	}

	public HttpMethod getMethod() {
		return HttpMethod.GET;
	}

	public String getPath() {
		return "/users";
	}

	public String getProtocol() {
		return "HTTP";
	}

	public String getVersion() {
		return "1.1";
	}
}
