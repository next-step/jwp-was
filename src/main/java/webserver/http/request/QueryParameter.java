package webserver.http.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class QueryParameter {
	private static final String PARAM_DELIMITER = "&";
	private static final String PARAM_KEY_VALUE_DELIMITER = "=";
	private static final int INDEX_KEY = 0;
	private static final int INDEX_VALUE = 1;

	private Map<String, String> parameter;

	private QueryParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}

	public static QueryParameter of(String stringParameters) {
		Map<String, String> parameters = Arrays.stream(stringParameters.split(PARAM_DELIMITER))
											   .map(s -> (s.split(PARAM_KEY_VALUE_DELIMITER)))
											   .collect(Collectors.toMap(s -> s[INDEX_KEY], s -> s[INDEX_VALUE]));
		return new QueryParameter(parameters);
	}

	public static QueryParameter of() {
		return new QueryParameter(Collections.emptyMap());
	}

	public String getValue(String key) {
		return parameter.get(key);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		QueryParameter that = (QueryParameter) o;
		return Objects.equals(parameter, that.parameter);
	}

	@Override
	public int hashCode() {
		return Objects.hash(parameter);
	}
}
