package webserver;

import utils.HttpMethod;

public class RequestLine {
	private static final String REQUEST_DELIMITER = " ";
	private static final String PROTOCOL_VERSION_DELIMITER = "/";
	private static final int REQUEST_PARSING_NUMBER = 3;
	private static final int PROTOCOL_VERSION_PARSING_NUMBER = 2;

	private String method;
	private String path;
	private String protocol;
	private String version;

	protected RequestLine() {

	}

	private RequestLine(String method, String path, String protocol, String version) {
		this.method = method;
		this.path = path;
		this.protocol = protocol;
		this.version = version;
	}

	public RequestLine parse(String request) {
		validateRequestNull(request);

		String[] parsingRequest = request.split(REQUEST_DELIMITER);
		validateParsingResult(parsingRequest);

		String[] parsingProtocolVersion = parsingRequest[2].split(PROTOCOL_VERSION_DELIMITER);
		validateProtocolVersionParsingResult(parsingProtocolVersion);

		return new RequestLine(parsingRequest[0], parsingRequest[1], parsingProtocolVersion[0],
			parsingProtocolVersion[1]);
	}

	private void validateProtocolVersionParsingResult(String[] parsingProtocolVersion) {
		if (parsingProtocolVersion.length != PROTOCOL_VERSION_PARSING_NUMBER) {
			throw new IllegalArgumentException(
				String.format("프로토콜과 버전에 대한 요청 정보의 갯수가 [%d]를 충족하지 못합니다.(요청된 정보의 갯수: [%d])",
					PROTOCOL_VERSION_PARSING_NUMBER, parsingProtocolVersion.length));
		}
	}

	private void validateParsingResult(String[] parsingRequest) {
		if (parsingRequest.length != REQUEST_PARSING_NUMBER) {
			throw new IllegalArgumentException(
				String.format("요청 정보의 갯수가 [%d]를 충족하지 못합니다.(요청된 정보의 갯수: [%d])",
					REQUEST_PARSING_NUMBER, parsingRequest.length));
		}

		if (!HttpMethod.matchedPropertyOf(parsingRequest[0])) {
			throw new IllegalArgumentException(
				String.format("요청 메서드는 GET 또는 POST이어야 합니다.(요청 메서드: [%s])", parsingRequest[0]));
		}
	}

	private void validateRequestNull(String request) {
		if (request == null || request.isEmpty()) {
			throw new IllegalArgumentException("요청 내용이 NULL 또는 비어있는 값 입니다.");
		}
	}

	public String getMethod() {
		return this.method;
	}

	public String getPath() {
		return this.path;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public String getVersion() {
		return this.version;
	}
}
