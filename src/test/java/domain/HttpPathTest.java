package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpPathTest {

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
