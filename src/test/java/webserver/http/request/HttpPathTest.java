package webserver.http.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpPathTest {

	@Test
	@DisplayName("빈 문자열 파싱")
	public void parseEmptyPath() {
		// given
		String empty = "";

		// when & then
		Assertions.assertThrows(IllegalArgumentException.class, () -> HttpPath.of(empty));
	}

	@Test
	@DisplayName("Path 파싱")
	public void parsePath() {
		// given
		String path = "/users";

		// when
		HttpPath httpPath = HttpPath.of(path);

		// then
		assertThat(httpPath.getPath()).isEqualTo("/users");
	}

	@Test
	@DisplayName("Query String 파싱")
	public void parseQueryString() {
		// given
		String pathWithQueryParameters = "/users?userId=javajigi&password=password&name=JaeSung";

		// when
		HttpPath httpPath = HttpPath.of(pathWithQueryParameters);

		// then
		assertThat(httpPath.getPath()).isEqualTo("/users");
		assertThat(httpPath.getParameter().getValue("userId")).isEqualTo("javajigi");
		assertThat(httpPath.getParameter().getValue("password")).isEqualTo("password");
		assertThat(httpPath.getParameter().getValue("name")).isEqualTo("JaeSung");
	}
}
