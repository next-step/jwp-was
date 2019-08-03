package webserver.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2019-08-03.
 */

public class RequestParameters {
	private List<Parameter> parameters;

	public RequestParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public static RequestParameters parse(String queryString) {
		return new RequestParameters(QueryStringParser.parseQueryString(queryString));
	}

	public Optional<String> getOne(String key) {
		return parameters.stream().
			filter(it -> it.equalsKey(key))
			.map(Parameter::getValue).findAny();
	}

	public List getAll(String key) {
		return parameters.stream().
			filter(it -> it.equalsKey(key))
			.map(Parameter::getValue)
			.distinct()
			.collect(Collectors.toList());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		RequestParameters that = (RequestParameters) o;

		return Objects.equals(parameters, that.parameters);

	}

	@Override
	public int hashCode() {
		return parameters != null ? parameters.hashCode() : 0;
	}

	private static class Parameter {
		private String key;
		private String value;

		public String getValue() {
			return value;
		}

		public Parameter(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public boolean equalsKey(String key) {
			return this.key.equals(key);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;

			Parameter parameter = (Parameter) o;

			if (!Objects.equals(key, parameter.key))
				return false;
			return Objects.equals(value, parameter.value);

		}

		@Override
		public int hashCode() {
			int result = key != null ? key.hashCode() : 0;
			result = 31 * result + (value != null ? value.hashCode() : 0);
			return result;
		}
	}

	private static class QueryStringParser {
		public static final String EQUALS = "=";
		public static final String AMPERSAND = "&";
		public static final int QUERY_MIN_SIZE = 2;
		public static final int QUERY_LIST_PREDICATE = 1;

		private static List<Parameter> parseQueryString(String queryString) {
			return Arrays.stream(queryString.split(AMPERSAND))
				.filter(StringUtils::isNotBlank)
				.map(QueryStringParser::parsingQuery)
				.filter(QueryStringParser::hasNotValue)
				.map(it -> new Parameter(it[0], it[1]))
				.collect(Collectors.toList());
		}

		private static Map.Entry<String, ?> getObjectOrList(Map.Entry<String, List<Object>> it) {
			return it.getValue().size() > QUERY_LIST_PREDICATE ? it : new AbstractMap.SimpleImmutableEntry<>(it.getKey(), it.getValue().get(0));
		}

		private static boolean hasNotValue(String[] it) {
			return it.length == QUERY_MIN_SIZE;
		}

		private static String[] parsingQuery(String it) {
			return it.split(EQUALS);
		}
	}
}
