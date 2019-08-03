package webserver.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.request.enums.HttpMethod;
import webserver.request.enums.HttpVersion;

import java.util.stream.Stream;

/**
 * Created by hspark on 2019-08-01.
 */
class RequestLineTest {

	@ParameterizedTest(name = "requestLine: {0}, parseResult : [ method : {1}, path : {2}, params : {3} ]")
	@MethodSource("parseRequestLine")
	void test_requestLine(String rawData, HttpMethod httpMethod, HttpVersion httpVersion, URI uri) {
		RequestLine requestLine = RequestLine.parse(rawData);

		Assertions.assertThat(requestLine.getHttpMethod()).isEqualTo(httpMethod);
		Assertions.assertThat(requestLine.getRequestUrl()).isEqualTo(uri);
		Assertions.assertThat(requestLine.getHttpVersion()).isEqualTo(httpVersion);
	}

	private static Stream<Arguments> parseRequestLine() {
		return Stream.of(
			Arguments.of("GET /users HTTP/1.1", HttpMethod.GET, HttpVersion.HTTP_1_1, URI.parse("/users")),
			Arguments.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1", HttpMethod.GET, HttpVersion.HTTP_1_1, URI.parse("/users?userId"
				+ "=javajigi"
				+ "&password"
				+ "=password&name=JaeSung"))
		);
	}
}