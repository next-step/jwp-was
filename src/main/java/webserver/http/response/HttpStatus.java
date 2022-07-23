package webserver.http.response;

import java.util.Arrays;

public enum HttpStatus {
	OK("OK", "200"),
	FOUND("Found", "302"),
	NOT_FOUND("Not Found", "404"),
	INTERNAL_SERVER_ERROR("Internal Server Error", "500");

	private static final String NOT_SUPPORTED_STATUS = "지원하지 않는 Http Status 입니다.";

	private final String message;
	private final String code;

	HttpStatus(String message, String code) {
		this.message = message;
		this.code = code;
	}

	public static HttpStatus of(String code) {
		return Arrays.stream(HttpStatus.values())
					 .filter(status -> status.code.equals(code))
					 .findAny()
					 .orElseThrow(() -> new IllegalArgumentException(NOT_SUPPORTED_STATUS));
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}
}
