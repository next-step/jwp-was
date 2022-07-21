package domain;

public class HttpProtocol {
	private static final String MESSAGE_INVALID_PROTOCOL_AND_VERSION = "유효하지 않은 프로토콜/버전 정보입니다.";
	private static final String DELIMITER = "/";
	private static final int PROTOCOL_VERSION_LENGTH = 2;
	private static final int INDEX_PROTOCOL = 0;
	private static final int INDEX_VERSION = 1;

	private final String protocol;
	private final String version;

	private HttpProtocol(String protocol, String version) {
		this.protocol = protocol;
		this.version = version;
	}

	public static HttpProtocol of(String attribute) {
		String[] versionOfProtocol = attribute.split(DELIMITER);
		if (versionOfProtocol.length != PROTOCOL_VERSION_LENGTH) {
			throw new IllegalArgumentException(MESSAGE_INVALID_PROTOCOL_AND_VERSION);
		}
		return new HttpProtocol(versionOfProtocol[INDEX_PROTOCOL], versionOfProtocol[INDEX_VERSION]);
	}

	public String getVersion() {
		return version;
	}

	public String getProtocol() {
		return protocol;
	}
}
