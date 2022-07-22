package model;

public class HttpProtocol {
	private static final String PROTOCOL_VERSION_DELIMITER = "/";
	private static final int PROTOCOL_VERSION_PARSING_NUMBER = 2;

	private String protocol;
	private String version;

	public HttpProtocol(String protocol, String version) {
		this.protocol = protocol;
		this.version = version;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getVersion() {
		return version;
	}

	public static HttpProtocol of(String httpProtocol) {
		String[] parsingProtocolVersion = httpProtocol.split(PROTOCOL_VERSION_DELIMITER);
		validateProtocolVersionParsingResult(parsingProtocolVersion);

		return new HttpProtocol(parsingProtocolVersion[0], parsingProtocolVersion[1]);
	}

	private static void validateProtocolVersionParsingResult(String[] parsingProtocolVersion) {
		if (parsingProtocolVersion.length != PROTOCOL_VERSION_PARSING_NUMBER) {
			throw new IllegalArgumentException(
				String.format("프로토콜과 버전에 대한 요청 정보의 갯수가 [%d]를 충족하지 못합니다.(요청된 정보의 갯수: [%d])",
					PROTOCOL_VERSION_PARSING_NUMBER, parsingProtocolVersion.length));
		}
	}
}
