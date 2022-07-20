package domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestLine {
	private static final String MESSAGE_INVALID_REQUEST_LINE = "유효하지 않은 Request Line 입니다.";
	private static final String REQUEST_LINE_DELIMITER = " ";
	private static final int REQUEST_LINE_LENGTH = 3;
	private static final int INDEX_METHOD = 0;
	private static final int INDEX_PATH = 1;
	private static final int INDEX_PROTOCOL_VERSION = 2;

	private HttpMethod httpMethod;
	private String httpPath;
	private HttpProtocol httpProtocol;
	private Map<String, String> parameters;

	public HttpRequestLine(String method, String path, String protocol) {
		this.httpMethod = HttpMethod.valueOf(method);
		this.httpPath = path.split("\\?")[0];
		this.httpProtocol = HttpProtocol.of(protocol);
		this.parameters = Arrays.stream(path.split("\\?")[1].split("&"))
								.map(s -> (s.split("=")))
								.collect(Collectors.toMap(s -> s[0], s -> s[1]));
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
		return httpProtocol.getProtocol();
	}

	public String getVersion() {
		return httpProtocol.getVersion();
	}

	public Map<String, String> getParameters() {
		return parameters;
	}
}
