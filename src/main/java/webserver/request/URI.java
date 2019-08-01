package webserver.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2019-08-01.
 */
public class URI {
	public static final String QUERY_STRING_DELIMETER = "\\?";
	public static final String EQUALS = "=";
	public static final String AMPERSAND = "&";
	public static final int QUERY_STRING_INDEX = 2;

	private String path;
	private String queryString;
	private Map<String, String> parameters;

	public URI(String path, String queryString) {
		this.path = path;
		this.queryString = queryString;
		this.parameters = parseQueryString(queryString);
	}

	public static URI parse(String uri) {
		String[] parsedUri = uri.split(QUERY_STRING_DELIMETER);
		String path = parsedUri[0];
		String queryString = parsedUri.length < QUERY_STRING_INDEX ? StringUtils.EMPTY : parsedUri[1];
		return new URI(path, queryString);
	}

	private Map<String, String> parseQueryString(String queryString) {
		String[] queries = queryString.split(AMPERSAND);
		return Arrays.stream(queries)
			.filter(it -> StringUtils.isNotBlank(it))
			.map(this::parseQuery)
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	private AbstractMap.SimpleEntry<String, String> parseQuery(String strs) {
		String[] query = strs.split(EQUALS);
		return new AbstractMap.SimpleEntry<>(query[0], query[1]);
	}

	public String getPath() {
		return path;
	}

	public String getQueryString() {
		return queryString;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}
}
