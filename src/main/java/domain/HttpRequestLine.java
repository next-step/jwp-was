package domain;

public class HttpRequestLine {
	private static final String MESSAGE_INVALID_REQUEST_LINE = "유효하지 않은 Request Line 입니다.";
	private static final String REQUEST_LINE_DELIMITER = " ";
	private static final int REQUEST_LINE_LENGTH = 3;
	private static final int INDEX_METHOD = 0;
	private static final int INDEX_PATH = 1;
	private static final int INDEX_PROTOCOL_VERSION = 2;

	private HttpMethod httpMethod;
	private String httpPath;
	private String protocol;
	private String version;

	public HttpRequestLine(String method, String path, String protocol) {
		this.httpMethod = HttpMethod.valueOf(method);
		this.httpPath = path;
		this.protocol = protocol.split("/")[0];
		this.version = protocol.split("/")[1];
	}

	public static HttpRequestLine of(String line) {
		String[] splitLine = line.split(REQUEST_LINE_DELIMITER);
		if (splitLine.length != REQUEST_LINE_LENGTH) {
			throw new IllegalArgumentException(MESSAGE_INVALID_REQUEST_LINE);
		}
		return new HttpRequestLine(splitLine[INDEX_METHOD], splitLine[INDEX_PATH], splitLine[INDEX_PROTOCOL_VERSION]);
	}

	public String getMethod() {
		return httpMethod.name();
	}

	public String getPath() {
		return httpPath;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getVersion() {
		return version;
	}
}
