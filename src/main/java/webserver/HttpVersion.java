package webserver;

public class HttpVersion {

	private static final String DELIMITER = "/";
	private static final int VALID_NUMBER_OF_TOKENS = 2;
	private static final int INDEX_OF_PROTOCOL = 0;
	private static final int INDEX_OF_VERSION = 1;

	private final String protocol;
	private final String version;

	public HttpVersion(String token) {
		String[] tokens = token.split(DELIMITER);
		if (tokens.length != VALID_NUMBER_OF_TOKENS) {
			throw new IllegalArgumentException();
		}
		this.protocol = tokens[INDEX_OF_PROTOCOL];
		this.version = tokens[INDEX_OF_VERSION];
	}

	public String getProtocol() {
		return protocol;
	}

	public String getVersion() {
		return version;
	}
}
