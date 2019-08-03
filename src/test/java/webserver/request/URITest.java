package webserver.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Created by hspark on 2019-08-01.
 */
class URITest {
	@DisplayName("URI 파싱, 쿼리스트링이 일부만 있는 경우")
	@ParameterizedTest(name = "url {0}, parsing result : [ path : {1} ]")
	@MethodSource("parseUrl")
	void test_parse_queryString(String url, String path) {
		URI uri = URI.parse(url);
		Assertions.assertThat(uri.getPath()).isEqualTo(path);
	}

	private static Stream<Arguments> parseUrl() {
		return Stream.of(
			Arguments.of("/users?userId=javajigi&password=password&name=JaeSung", "/users"),
			Arguments.of("/users?userId=&password=password&name=JaeSung", "/users"),
			Arguments.of("/users", "/users"));
	}
}