package webserver;

import exception.IllegalHttpProtocolException;

public class HttpProtocol {

	private static final String DELIMITER = "/";
	private static final int VALID_NUMBER_OF_TOKENS = 2;
	private static final int INDEX_OF_PROTOCOL = 0;
	private static final int INDEX_OF_VERSION = 1;

	private final String protocol;
	private final String version;

	public HttpProtocol(String token) {
		String[] tokens = token.split(DELIMITER);
		validate(tokens);
		this.protocol = tokens[INDEX_OF_PROTOCOL];
		this.version = tokens[INDEX_OF_VERSION];
	}

	private void validate(String[] tokens) {
		if (tokens.length != VALID_NUMBER_OF_TOKENS) {
			throw new IllegalHttpProtocolException(tokens);
		}
	}

	public String getProtocol() {
		return protocol;
	}

	public String getVersion() {
		return version;
	}
}
