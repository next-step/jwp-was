package webserver.request;

import webserver.request.enums.HttpMethod;
import webserver.request.enums.HttpVersion;

/**
 * Created by hspark on 2019-08-01.
 */
public class RequestLine {
	public static final String BLANK = " ";

	private HttpMethod httpMethod;
	private String requestUrl;
	private HttpVersion httpVersion;

	public RequestLine(String httpMethod, String requestUrl, String httpVersion) {
		this.httpMethod = HttpMethod.valueOf(httpMethod);
		this.requestUrl = requestUrl;
		this.httpVersion = HttpVersion.findByHeaderValue(httpVersion);
	}

	public static RequestLine parse(String requestLineStr) {
		String[] requestLineArr = requestLineStr.split(BLANK);
		return new RequestLine(requestLineArr[0], requestLineArr[1], requestLineArr[2]);
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public HttpVersion getHttpVersion() {
		return httpVersion;
	}
}
