package webserver;

import java.util.Objects;

import exception.IllegalProtocolException;

public class Protocol {

	private static final String DELIMITER = "/";
	private static final int VALID_NUMBER_OF_TOKENS = 2;
	private static final int INDEX_OF_PROTOCOL = 0;
	private static final int INDEX_OF_VERSION = 1;

	private final String protocol;
	private final String version;

	public Protocol(String token) {
		String[] tokens = token.split(DELIMITER);
		validate(tokens);
		this.protocol = tokens[INDEX_OF_PROTOCOL];
		this.version = tokens[INDEX_OF_VERSION];
	}

	public Protocol(String protocol, String version) {
		this.protocol = protocol;
		this.version = version;
	}

	private void validate(String[] tokens) {
		if (tokens.length != VALID_NUMBER_OF_TOKENS) {
			throw new IllegalProtocolException(tokens);
		}
	}

	public String getProtocol() {
		return protocol;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Protocol protocol1 = (Protocol)o;
		return Objects.equals(protocol, protocol1.protocol) && Objects.equals(version,
			protocol1.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(protocol, version);
	}
}
