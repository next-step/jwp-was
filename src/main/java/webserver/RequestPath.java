package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestPath {

	private static final String PATH_DELIMITER = "\\?";
	private static final String QUERY_DELIMITER = "&";
	private static final String KEY_VALUE_DELIMITER = "=";

	private final String path;
	private Map<String, String> query = new HashMap<>();

	public RequestPath(final String path) {
		final String[] split = path.split(PATH_DELIMITER);
		
		this.path = split[0];
		
		if (split.length > 1) {
			this.query = setQuery(split[1]);
		}
	}

	private Map<String, String> setQuery(final String queryValue) {
		final String[] splitQuery = queryValue.split(QUERY_DELIMITER);

		final Map<String, String> queries = new HashMap<>();

		for (String query : splitQuery) {
			final String[] value = query.split(KEY_VALUE_DELIMITER);
			queries.put(value[0], value[1]);
		}
		
		return queries;
	}

	public String getPath() {
		return path;
	}

	public Map<String, String> getQuery() {
		return query;
	}

	@Override public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final RequestPath that = (RequestPath) o;
		return Objects.equals(path, that.path);
	}

	@Override public int hashCode() {
		return Objects.hash(path);
	}
}
