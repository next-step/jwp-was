package webserver.http.request;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpRequestBody {
	private static final String BODY_DELIMITER = "&";
	private static final String BODY_KEY_VALUE_DELIMITER = "=";
	private static final int INDEX_KEY = 0;
	private static final int INDEX_VALUE = 1;

	private final Map<String, String> body;

	private HttpRequestBody(Map<String, String> body) {
		this.body = body;
	}

	public static HttpRequestBody of(String body) {
		if (body.isBlank()) {
			return new HttpRequestBody(Collections.emptyMap());
		}
		Map<String, String> bodyMap = Stream.of(body.split(BODY_DELIMITER))
											.map(x -> x.split(BODY_KEY_VALUE_DELIMITER))
											.collect(Collectors.toMap(x -> x[INDEX_KEY], x -> x[INDEX_VALUE]));
		return new HttpRequestBody(bodyMap);
	}

	public Map<String, String> getBody() {
		return body;
	}

	public String getAttribute(String key) {
		return body.get(key);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HttpRequestBody that = (HttpRequestBody) o;
		return Objects.equals(body, that.body);
	}

	@Override
	public int hashCode() {
		return Objects.hash(body);
	}
}
