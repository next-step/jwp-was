package webserver.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.util.MultiValueMap;

class HttpRequestDecoderTest {
	HttpMessageGenerator messageGenerator;

	@BeforeEach
	void setUp() {
		messageGenerator = new HttpMessageGenerator();
	}

	@DisplayName("request line uses only one ows as a delimiter.")
	@Test
	void throwsExceptionWhenNotSingleOWS() {
		String expectedMessage = "Invalid separator. only a single space or horizontal tab allowed.";
		Class expectedException = IllegalArgumentException.class;

		String message = "POST  /hello.html?key=value&page=1 HTTP/1.1\r\n";

		assertThatThrownBy(() -> {
			messageGenerator.generateRequestLine(message);
		}).isInstanceOf(expectedException)
			.hasMessage(expectedMessage);
	}

	@DisplayName("request line should consist of three elements.")
	@Test
	void throwsExceptionWhenBadRequestLine() {
		String expectedMessage = "Invalid argument count exception. There must be 3 elements, but received (2)";
		Class expectedException = IllegalArgumentException.class;

		String message = "POST/hello.html?key=value&page=1 HTTP/1.1\r\n";

		assertThatThrownBy(() -> {
			messageGenerator.generateRequestLine(message);
		}).isInstanceOf(expectedException)
			.hasMessage(expectedMessage);
	}

	@Test
	void decodeRequestLine() {
		String message = "GET /hello.html?key=value&page=1 HTTP/1.1\r\n";
		RequestLine requestLine = messageGenerator.generateRequestLine(message);

		assertThat(requestLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
		assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
		assertThat(requestLine.getUri().getPath()).isEqualTo("/hello.html");
		assertThat(requestLine.getUri().getQuery()).isEqualTo("key=value&page=1");
	}

	@ParameterizedTest
	@MethodSource("provideArguments")
	void decodeHeaders(String key, String value) throws IOException {
		MultiValueMap<String, String> headers = messageGenerator.generateHeaders();

		assertThat(headers.getFirst(key)).isEqualTo(value);
	}

	private static Stream<Arguments> provideArguments() {
		return Stream.of(
			arguments("User-Agent", "Mozilla/4.0 (compatible; MSIE5.01; Windows NT)"),
			arguments("Cookie", "JSESSIONID=50f59aa2-3d3b-451c-9aaa-9b5d5d5db6d5; utma=9384732"),
			arguments("Accept-Language", "en-us")
		);
	}
}