package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

class HttpRequestLineTest {

	/**
	 * GET /users HTTP/1.1
	 */
	@DisplayName("GET 요청 파싱")
	@Test
	void parse_get() {
		final String requestLine = "GET /users HTTP/1.1";

		HttpRequestLine httpRequestLine = new HttpRequestLine(requestLine);

		assertAll(
				() -> assertThat(httpRequestLine.getHttpMethod()).isEqualTo(HttpMethod.GET),
				() -> assertThat(httpRequestLine.getPath().getPath()).isEqualTo("/users"),
				() -> assertThat(httpRequestLine.getProtocol().getProtocol()).isEqualTo("HTTP"),
				() -> assertThat(httpRequestLine.getVersion().getVersion()).isEqualTo("1.1")
		);
	}
}
