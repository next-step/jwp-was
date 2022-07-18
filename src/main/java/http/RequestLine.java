package http;

import http.request.HttpMethod;
import http.request.Protocol;

public class RequestLine {

	private static final String DELIMITER = " ";

	private final HttpMethod httpMethod;
	private final String path;
	private final Protocol protocol;

	public RequestLine(String line) {
		var data = line.split(DELIMITER);
		this.httpMethod = HttpMethod.valueOf(data[0]);
		this.path = data[1];
		this.protocol = Protocol.of(data[2]);
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
}
