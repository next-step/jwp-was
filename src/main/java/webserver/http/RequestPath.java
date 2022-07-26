package webserver.http;

import java.util.regex.Pattern;

import exception.IllegalRequestPathException;

public class RequestPath {

	private static final String QUESTION_MARK = "?";
	private static final String SLASH = "/";
	private static final int INDEX_ZERO = 0;
	private static final int INDEX_ONE = 1;

	private final String path;
	private final RequestBody requestBody;

	private RequestPath(String path) {
		this.path = path;
		this.requestBody = null;
	}

	private RequestPath(String path, String queryString) {
		this.path = path;
		this.requestBody = new RequestBody(queryString);
	}

	public static RequestPath from(String requestPath) {
		validate(requestPath);
		if (requestPath.contains(QUESTION_MARK)) {
			String[] tokens = requestPath.split(Pattern.quote(QUESTION_MARK));
			String path = tokens[INDEX_ZERO];
			String queryString = tokens[INDEX_ONE];
			return new RequestPath(path, queryString);
		}
		return new RequestPath(requestPath);
	}

	private static void validate(String path) {
		if (!path.startsWith(SLASH)) {
			throw new IllegalRequestPathException(path);
		}
	}

	public String getPath() {
		return path;
	}

	public String getQueryString() {
		if (requestBody == null) {
			return null;
		}
		return requestBody.getQueryString();
	}

	public String getParameter(String name) {
		if (requestBody == null) {
			return null;
		}
		return requestBody.getParameter(name);
	}
}
