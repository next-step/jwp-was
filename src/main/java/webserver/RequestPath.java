package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import exception.IllegalRequestPathException;

public class RequestPath {

	private static final String QUESTION_MARK = "?";
	private static final String AMPERSAND = "&";
	private static final String SLASH = "/";
	private static final String EQUAL_SIGN = "=";
	private static final int INDEX_ZERO = 0;
	private static final int INDEX_ONE = 1;

	private final String path;
	private final String queryString;
	private final MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

	private RequestPath(String path) {
		this.path = path;
		this.queryString = null;
	}

	private RequestPath(String path, String queryString) {
		this.path = path;
		this.queryString = queryString;
		parseQueryString(queryString);
	}

	private void parseQueryString(String queryString) {
		Arrays.stream(queryString.split(AMPERSAND)).forEach(parameter -> {
			String[] nameValuePair = parameter.split(EQUAL_SIGN);
			parameters.put(nameValuePair[INDEX_ZERO], Collections.singletonList(nameValuePair[INDEX_ONE]));
		});
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
		return queryString;
	}

	public String getParameter(String name) {
		List<String> values = this.parameters.get(name);
		if (values.isEmpty()) {
			return null;
		}
		return values.get(INDEX_ZERO);
	}
}
