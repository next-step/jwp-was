package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeaders {
	private static final String HEADER_DELIMITER = ":";
	private static final int INDEX_KEY = 0;
	private static final int INDEX_VALUE = 1;

	private static final Map<String , String> headers = new HashMap<>();

	private HttpRequestHeaders() {
	}

	public static HttpRequestHeaders of(List<String> lines) {
		for (String line : lines) {
			String[] tokens = line.split(HEADER_DELIMITER);
			headers.put(tokens[INDEX_KEY].trim(), tokens[INDEX_VALUE].trim());
		}
		return new HttpRequestHeaders();
	}
}
