package webserver;

import exception.IllegalRequestLineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLine {

	private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);
	private static final String DELIMITER = " ";
	private static final int VALID_NUMBER_OF_TOKENS = 3;
	private static final int INDEX_OF_METHOD = 0;
	private static final int INDEX_OF_PATH = 1;
	private static final int INDEX_OF_HTTP_PROTOCOL = 2;

	private final HttpMethod method;
	private final RequestPath requestPath;
	private final Protocol protocol;

	public RequestLine(String value) {
		String[] tokens = value.split(DELIMITER);
		validate(tokens);
		this.method = HttpMethod.from(tokens[INDEX_OF_METHOD]);
		this.requestPath = RequestPath.from(tokens[INDEX_OF_PATH]);
		this.protocol = new Protocol(tokens[INDEX_OF_HTTP_PROTOCOL]);
		logger.debug("request line = {}", value);
	}

	private void validate(String[] tokens) {
		if (tokens.length != VALID_NUMBER_OF_TOKENS) {
			throw new IllegalRequestLineException(tokens);
		}
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getPath() {
		return requestPath.getPath();
	}

	public String getProtocol() {
		return protocol.getProtocol();
	}

	public String getVersion() {
		return protocol.getVersion();
	}

	public String getQueryString() {
		return requestPath.getQueryString();
	}

	public String getParameter(String name) {
		return requestPath.getParameter(name);
	}
}
