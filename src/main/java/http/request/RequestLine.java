package http.request;

import java.util.Map;

public class RequestLine {

	private static final String REQUEST_LINE_DELIMITER = " ";
	private static final String QUERY_PARAMETER_DELIMITER = "\\?";

	private static final int ONLY_PATH = 1;

	private final HttpMethod httpMethod;
	private final String path;
	private final Protocol protocol;
	private final QueryParams queryParams;

	public RequestLine(String line) {
		var specs = line.split(REQUEST_LINE_DELIMITER);

		this.httpMethod = HttpMethod.valueOf(specs[0]);
		this.protocol = Protocol.of(specs[2]);

		var pathWithParameters = specs[1]
			.split(QUERY_PARAMETER_DELIMITER);
		this.path = pathWithParameters[0];
		this.queryParams = parseParameters(pathWithParameters);
	}

	private QueryParams parseParameters(String[] pathWithParameters) {
		if (pathWithParameters.length == ONLY_PATH) {
			return QueryParams.EMPTY;
		}
		return QueryParams.of(pathWithParameters[1]);
	}

	public String getMethod() {
		return httpMethod.name();
	}

	public String getPath() {
		return path;
	}

	public String getProtocol() {
		return protocol.getProtocolType()
			.name();
	}

	public String getVersion() {
		return protocol.getVersion();
	}

	public Map<String, String> getQueryParams() {
		return queryParams.get();
	}
}
