package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineTest {

	@DisplayName("Requestline GET 객체를 생성할 수 있다.")
	@Test
	void GET_객체_생성() {
		var line = "GET /users HTTP/1.1";

		var requestLine = new RequestLine(line);

		assertAll(
			() -> assertThat(requestLine.getMethod()).isEqualTo("GET"),
			() -> assertThat(requestLine.getPath()).isEqualTo("/users"),
			() -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
			() -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
		);
	}

	@DisplayName("RequestLine POST 객체를 생성할 수 있다.")
	@Test
	void POST_객체_생성() {
		var line = "POST /users HTTP/1.1";

		var requestLine = new RequestLine(line);

		assertAll(
			() -> assertThat(requestLine.getMethod()).isEqualTo("POST"),
			() -> assertThat(requestLine.getPath()).isEqualTo("/users"),
			() -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
			() -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
		);
	}
}