package webserver;

import model.HttpPath;
import model.HttpProtocol;
import utils.HttpMethod;

public class RequestLine {
	private static final String REQUEST_DELIMITER = " ";
	private static final int REQUEST_PARSING_NUMBER = 3;

	private HttpMethod method;
	private HttpPath path;
	private HttpProtocol protocol;

	protected RequestLine() {

	}

	private RequestLine(HttpMethod method, HttpPath path, HttpProtocol protocol) {
		this.method = method;
		this.path = path;
		this.protocol = protocol;
	}

	public RequestLine parse(String request) {
		validateRequestNull(request);

		String[] parsingRequest = request.split(REQUEST_DELIMITER);
		validateParsingResult(parsingRequest);

		return new RequestLine(HttpMethod.valueOf(parsingRequest[0]), new HttpPath(parsingRequest[1]),
			new HttpProtocol(parsingRequest[2]));
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

	public HttpMethod getMethod() {
		return this.method;
	}

	public HttpPath getHttpPath() {
		return this.path;
	}

	public HttpProtocol getHttpProtocol() {
		return this.protocol;
	}

	public RequestPathQueryString getRequestPathQueryString() {
		return this.path.getQueryString();
	}
}
