package webserver.http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.util.MultiValueMap;

class QueryStringDecoderTest {
	@Test
	void parseQueryString() {
		String queryString = "name=alice&age=20&name=bob&age=30&page=1\r\n";
		QueryStringDecoder decoder = new QueryStringDecoder();

		MultiValueMap<String, String> parameters = decoder.parseQueryString(queryString);

		assertThat(parameters.get("name")).containsExactly("alice", "bob");
		assertThat(parameters.get("age")).containsExactly("20", "30");
		assertThat(parameters.get("page")).containsExactly("1");
	}

	@ParameterizedTest
	@NullAndEmptySource
	void parseEmptyQueryString(String queryString) {
		QueryStringDecoder decoder = new QueryStringDecoder();

		MultiValueMap<String, String> parameters = decoder.parseQueryString(queryString);
		assertThat(parameters).isEmpty();
	}
}