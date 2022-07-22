package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestPathQueryString {
	private static final String ELEMENT_DELIMITER = "&";
	private static final String KEY_VALUE_DELIMITER = "=";
	private static final int KEY_INDEX = 0;
	private static final int VALUE_INDEX = 1;

	private Map<String, String> queryStringOfPath = new HashMap<>();

	public RequestPathQueryString(String queryString) {
		validateNullOrEmpty(queryString);
		this.queryStringOfPath = parsingQueryString(queryString);
	}

	public RequestPathQueryString() {

	}

	private Map<String, String> parsingQueryString(String queryString) {
		return Stream.of(queryString.split(ELEMENT_DELIMITER))
			.map(elements -> elements.split(KEY_VALUE_DELIMITER))
			.collect(Collectors.toMap(element -> element[KEY_INDEX], element -> element[VALUE_INDEX]));
	}

	private void validateNullOrEmpty(String queryString) {
		if (queryString == null || queryString.isEmpty()) {
			throw new IllegalArgumentException("path에 입력된 query string이 null 또는 빈값입니다.");
		}
	}

	public Map<String, String> getQueryStringOfPath() {
		return queryStringOfPath;
	}
}
