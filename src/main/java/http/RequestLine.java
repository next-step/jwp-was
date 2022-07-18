package http;

import java.util.Map;

import http.request.HttpMethod;
import http.request.Protocol;
import http.request.QueryParams;

public class RequestLine {

	private static final String REQUEST_LINE_DELIMITER = " ";
	private static final String QUERY_PARAMETER_DELIMITER = "\\?";

	private static final int PATH_ONLY_LENGTH = 1;
	private static final int PATH_WITH_PARAMETER_INDEX = 1;
	private static final int QUERY_PARAM_INDEX = 1;

	private final HttpMethod httpMethod;
	private final String path;
	private final Protocol protocol;
	private final QueryParams queryParams;

	public RequestLine(String line) {
		var data = line.split(REQUEST_LINE_DELIMITER);
		var pathWithParameters = data[PATH_WITH_PARAMETER_INDEX].split(QUERY_PARAMETER_DELIMITER);

		this.httpMethod = HttpMethod.valueOf(data[0]);
		this.path = pathWithParameters[0];
		this.queryParams = parseParameters(pathWithParameters);
		this.protocol = Protocol.of(data[2]);
	}

	private QueryParams parseParameters(String[] pathWithParameters) {
		if (pathWithParameters.length == PATH_ONLY_LENGTH) {
			return QueryParams.EMPTY;
		}
		return QueryParams.of(pathWithParameters[QUERY_PARAM_INDEX]);
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
