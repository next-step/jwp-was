package webserver.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2019-08-03.
 */

public class RequestParameters {
	private Map<String, Object> parameters;

	public RequestParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public static RequestParameters parse(String queryString) {
		return new RequestParameters(QueryStringParser.parse(queryString));
	}

	public <T> T get(String key, Class<T> clazz) {
		return clazz.cast(parameters.get(key));
	}

	public Object getObject(String key) {
		return parameters.get(key);
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

	private static class QueryStringParser {
		public static final String EQUALS = "=";
		public static final String AMPERSAND = "&";
		public static final int QUERY_MIN_SIZE = 2;
		public static final int QUERY_LIST_PREDICATE = 1;

		/**
		 * QueryString에서 값이 하나인 객체일 경우는 하나로, 여러개일 경우에는 List를 가지도록 한다.
		 * @param queryString
		 * @return
		 */
		private static Map<String, Object> parse(String queryString) {
			return parseQueryString(queryString)
				.entrySet()
				.stream()
				.map(QueryStringParser::getObjectOrList)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}

		/**
		 * QueryString을 List로 파싱한다.
		 * @param queryString
		 * @return
		 */
		private static Map<String, List<Object>> parseQueryString(String queryString) {
			return Arrays.stream(queryString.split(AMPERSAND))
				.filter(StringUtils::isNotBlank)
				.map(QueryStringParser::parsingQuery)
				.filter(QueryStringParser::hasNotValue)
				.map(it -> new AbstractMap.SimpleImmutableEntry<String, Object>(it[0], it[1]))
				.collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
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
