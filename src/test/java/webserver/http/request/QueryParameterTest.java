package webserver.http.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class QueryParameterTest {
	@Test
	public void parseQueryString() {
		// given
		String query = "userId=javajigi&password=password&name=JaeSung";

		// when
		QueryParameter queryParameter = QueryParameter.of(query);

		// then
		assertThat(queryParameter.getValue("userId")).isEqualTo("javajigi");
		assertThat(queryParameter.getValue("password")).isEqualTo("password");
		assertThat(queryParameter.getValue("name")).isEqualTo("JaeSung");
	}
}
