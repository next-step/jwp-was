package webserver;

import exception.IllegalRequestLineException;

public class RequestLine {

	private static final String DELIMITER = " ";
	private static final int VALID_NUMBER_OF_TOKENS = 3;
	private static final int INDEX_OF_METHOD = 0;
	private static final int INDEX_OF_PATH = 1;
	private static final int INDEX_OF_HTTP_VERSION = 2;

	private final HttpMethod method;
	private final RequestPath requestPath;
	private final HttpVersion httpVersion;

	public RequestLine(String value) {
		String[] tokens = value.split(DELIMITER);
		validate(tokens);
		this.method = HttpMethod.valueOf(tokens[INDEX_OF_METHOD]);
		this.requestPath = RequestPath.from(tokens[INDEX_OF_PATH]);
		this.httpVersion = new HttpVersion(tokens[INDEX_OF_HTTP_VERSION]);
	}

	private void validate(String[] tokens) {
		if (tokens.length != VALID_NUMBER_OF_TOKENS) {
			throw new IllegalRequestLineException(tokens);
		}
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getRequestPath() {
		return requestPath.getPath();
	}

	public String getProtocol() {
		return httpVersion.getProtocol();
	}

	public String getVersion() {
		return httpVersion.getVersion();
	}

	public String getQueryString() {
		return requestPath.getQueryString();
	}

	public String getParameter(String name) {
		return requestPath.getParameter(name);
	}
}
