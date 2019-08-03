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
 * Created by hspark on 2019-08-03.
 */
class RequestParametersTest {

	@Test
	@DisplayName("URI 파싱, 동일한 쿼리가 여러개 있는 경우")
	void test_parse_same_queryString() {
		String queryString = "password=password&name=JaeSung&name=JaeSung1";
		RequestParameters parameters = RequestParameters.parse(queryString);
		Assertions.assertThat(parameters.get("password", String.class)).isEqualTo("password");
		Assertions.assertThat(parameters.get("name", List.class)).hasSize(2);
		Assertions.assertThat(parameters.get("name", List.class)).contains("JaeSung", "JaeSung1");
		Assertions.assertThat(parameters.get("userId", String.class)).isNull();
	}

	@ParameterizedTest(name = "queryString {0}, parsing result : [ password : {1}, name : {2}, userId : {3} ]")
	@DisplayName("쿼리스트링 파싱")
	@MethodSource("parsingQueryString")
	void test_parse_partitial_queryString(String queryString, String password, String userId, String name) {
		RequestParameters parameters = RequestParameters.parse(queryString);
		Assertions.assertThat(parameters.get("password", String.class)).isEqualTo(password);
		Assertions.assertThat(parameters.get("name", String.class)).isEqualTo(name);
		Assertions.assertThat(parameters.get("userId", String.class)).isEqualTo(userId);
	}

	private static Stream<Arguments> parsingQueryString() {
		return Stream.of(
			Arguments.of("userId=javajigi&password=password&name=JaeSung", "password", "javajigi", "JaeSung"),
			Arguments.of("userId=&password=password&name=JaeSung", "password", null, "JaeSung"));
	}

}