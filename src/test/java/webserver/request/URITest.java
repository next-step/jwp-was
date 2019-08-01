package webserver.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by hspark on 2019-08-01.
 */
class URITest {

	@Test
	void test_parse() {
		String url = "/users";
		String queryString = "userId=javajigi&password=password&name=JaeSung";
		String uriStr = url + "?" + queryString;
		URI uri = URI.parse(uriStr);

		Assertions.assertThat(uri.getPath()).isEqualTo(url);
		Assertions.assertThat(uri.getQueryString()).isEqualTo(queryString);
		Assertions.assertThat(uri.getParameters()).hasSize(3);
		Assertions.assertThat(uri.getParameters().get("userId")).isEqualTo("javajigi");
		Assertions.assertThat(uri.getParameters().get("password")).isEqualTo("password");
		Assertions.assertThat(uri.getParameters().get("name")).isEqualTo("JaeSung");
	}

	@Test
	@DisplayName("URI 파싱, 쿼리스트링이 없는 경우")
	void test_parse_no_queryString() {
		String url = "/users";
		URI uri = URI.parse(url);

		Assertions.assertThat(uri.getPath()).isEqualTo(url);
		Assertions.assertThat(uri.getParameters()).hasSize(0);
		Assertions.assertThat(uri.getQueryString()).isBlank();
	}
}