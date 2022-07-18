package webserver;

public class RequestLine {

	private static final String DELIMITER = " ";
	private static final int VALID_NUMBER_OF_TOKENS = 3;
	private static final int INDEX_OF_METHOD = 0;
	private static final int INDEX_OF_PATH = 1;
	private static final int INDEX_OF_HTTP_VERSION = 2;

	private final HttpMethod method;
	private final String path;
	private final HttpVersion httpVersion;

	public RequestLine(String value) {
		String[] tokens = value.split(DELIMITER);
		if (tokens.length != VALID_NUMBER_OF_TOKENS) {
			throw new IllegalArgumentException();
		}
		this.method = HttpMethod.valueOf(tokens[INDEX_OF_METHOD]);
		this.path = tokens[INDEX_OF_PATH];
		this.httpVersion = new HttpVersion(tokens[INDEX_OF_HTTP_VERSION]);
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public String getProtocol() {
		return httpVersion.getProtocol();
	}

	public String getVersion() {
		return httpVersion.getVersion();
	}
}
