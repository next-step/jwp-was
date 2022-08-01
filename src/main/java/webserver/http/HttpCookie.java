package webserver.http;

public class HttpCookie {

	private static final String DEFAULT_PATH = "/";

	private final String name;
	private final String value;
	private final String path;

	public HttpCookie(String name, String value, String path) {
		this.name = name;
		this.value = value;
		this.path = path;
	}

	public HttpCookie(String name, String value) {
		this.name = name;
		this.value = value;
		this.path = DEFAULT_PATH;
	}

	public String toResponseString() {
		return "Set-Cookie: " + name + "=" + value + "; path=" + path + ";";
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public String getPath() {
		return this.path;
	}
}
