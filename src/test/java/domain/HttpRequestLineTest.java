package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestLineTest {

	@Test
	@DisplayName("지원하지 않는 메서드 파싱")
	public void parseNotSupportedMethod() {
		// given
		String line = "PUT /users HTTP/1.1";
		// when & then
		Assertions.assertThrows(IllegalArgumentException.class, () -> HttpRequestLine.of(line));
	}

	@Test
	@DisplayName("GET 메서드 파싱")
	public void parseGetMethod() {
		// given
		String line = "GET /users HTTP/1.1";

		// when
		HttpRequestLine requestLine = HttpRequestLine.of(line);

		// then
		assertThat(requestLine.getMethod()).isEqualTo("GET");
		assertThat(requestLine.getPath()).isEqualTo("/users");
		assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
		assertThat(requestLine.getVersion()).isEqualTo("1.1");
	}

	@Test
	@DisplayName("POST 메서드 파싱")
	public void parsePostMethod() {
		// given
		String line = "POST /users HTTP/1.1";

		// when
		HttpRequestLine requestLine = HttpRequestLine.of(line);

		// then
		assertThat(requestLine.getMethod()).isEqualTo("POST");
		assertThat(requestLine.getPath()).isEqualTo("/users");
		assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
		assertThat(requestLine.getVersion()).isEqualTo("1.1");
	}

	@Test
	@DisplayName("Query String 파싱")
	public void parseQueryString() {
		// given
		String line = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

		// when
		HttpRequestLine requestLine = HttpRequestLine.of(line);

		// then
		assertThat(requestLine.getMethod()).isEqualTo("GET");
		assertThat(requestLine.getPath()).isEqualTo("/users");
		assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
		assertThat(requestLine.getVersion()).isEqualTo("1.1");
		assertThat(requestLine.getParameters().get("userId")).isEqualTo("javajigi");
		assertThat(requestLine.getParameters().get("password")).isEqualTo("password");
		assertThat(requestLine.getParameters().get("name")).isEqualTo("JaeSung");
	}
}
