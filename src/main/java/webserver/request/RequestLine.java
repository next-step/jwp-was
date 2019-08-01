package webserver.request;

import webserver.request.enums.HttpMethod;
import webserver.request.enums.HttpVersion;

/**
 * Created by hspark on 2019-08-01.
 */
public class RequestLine {
	private HttpMethod httpMethod;
	private String requestUrl;
	private HttpVersion httpVersion;

	private RequestLine(String requestLineStr) {
		String[] requestLineArr = requestLineStr.split(" ");
		httpMethod = HttpMethod.valueOf(requestLineArr[0]);
		requestUrl = requestLineArr[1];
		httpVersion = HttpVersion.findByHeaderValue(requestLineArr[2]);
	}

	public static RequestLine parse(String requestLineStr) {
		return new RequestLine(requestLineStr);
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
