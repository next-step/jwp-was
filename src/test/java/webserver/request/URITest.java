package webserver.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by hspark on 2019-08-01.
 */
class URITest {
	@DisplayName("URI 파싱, 쿼리스트링이 일부만 있는 경우")
	@ParameterizedTest(name = "url {0}, parsing result : [ path : {1}, password : {2}, name : {3}, userId : {4} ]")
	@MethodSource("parseUrl")
	void test_parse_queryString(String url, String path, String password, String userId, String name) {
		URI uri = URI.parse(url);
		Assertions.assertThat(uri.getPath()).isEqualTo(path);
		Assertions.assertThat(uri.get("password", String.class)).isEqualTo(password);
		Assertions.assertThat(uri.get("name", String.class)).isEqualTo(name);
		Assertions.assertThat(uri.get("userId", String.class)).isEqualTo(userId);
	}

	@Test
	@DisplayName("URI 파싱, 동일한 쿼리가 여러개 있는 경우")
	void test_parse_same_queryString() {
		String url = "/users";
		String queryString = "password=password&name=JaeSung&name=JaeSung1";
		String uriStr = url + "?" + queryString;
		URI uri = URI.parse(uriStr);

		Assertions.assertThat(uri.getPath()).isEqualTo(url);
		Assertions.assertThat(uri.get("password", String.class)).isEqualTo("password");
		Assertions.assertThat(uri.get("name", List.class)).hasSize(2);
		Assertions.assertThat(uri.get("name", List.class)).contains("JaeSung", "JaeSung1");
		Assertions.assertThat(uri.get("userId", String.class)).isNull();
	}

	private static Stream<Arguments> parseUrl() {
		return Stream.of(
			Arguments.of("/users?userId=javajigi&password=password&name=JaeSung", "/users", "password", "javajigi", "JaeSung"),
			Arguments.of("/users?userId=&password=password&name=JaeSung", "/users", "password", null, "JaeSung"),
			Arguments.of("/users", "/users", null, null, null));
	}
}