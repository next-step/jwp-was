package model;

import webserver.RequestPathQueryString;

public class HttpPath {
	private static final String QUERY_STRING_DELIMITER = "\\?";

	private String path;
	private RequestPathQueryString queryString;

	public HttpPath(String path, RequestPathQueryString queryString) {
		this.path = path;
		this.queryString = queryString;
	}

	public HttpPath (String path) {
		RequestPathQueryString requestPathQueryString = new RequestPathQueryString();
		if(path.contains("?")) {
			String[] parsingPath = path.split(QUERY_STRING_DELIMITER);
			path = parsingPath[0];
			String stringQuery = parsingPath[1];
			requestPathQueryString = parsingQueryString(stringQuery);
		}

		this.path = path;
		this.queryString = requestPathQueryString;
	}

	public String getPath() {
		return path;
	}

	public RequestPathQueryString getQueryString() {
		return queryString;
	}

	private static RequestPathQueryString parsingQueryString(String path) {
		return new RequestPathQueryString(path);
	}
}
