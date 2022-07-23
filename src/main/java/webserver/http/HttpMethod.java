package webserver.http;

import java.util.Arrays;

public enum HttpMethod {
	GET, POST;

	private static final String NOT_FOUND_METHOD = "해당하는 HTTP 메서드를 찾을 수 없습니다.";

	public static HttpMethod of(String method) {
		return Arrays.stream(HttpMethod.values())
				.filter(httpMethod -> httpMethod.name().equalsIgnoreCase(method))
				.findAny().orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_METHOD));
	}

	public static Boolean isGet(HttpMethod httpMethod) {
		return httpMethod.equals(GET);
	}
}
