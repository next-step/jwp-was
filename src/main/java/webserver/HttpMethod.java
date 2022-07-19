package webserver;

import java.util.Arrays;

import exception.IllegalHttpMethodException;

public enum HttpMethod {
	GET("GET"),
	POST("POST");

	private final String value;

	HttpMethod(String value) {
		this.value = value;
	}

	public static HttpMethod from(String value) {
		return Arrays.stream(values())
			.filter(method -> method.value.equalsIgnoreCase(value))
			.findFirst()
			.orElseThrow(() -> new IllegalHttpMethodException(value));
	}
}
