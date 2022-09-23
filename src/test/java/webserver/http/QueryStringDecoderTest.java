package webserver.http;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;

class QueryStringDecoderTest {
	@Test
	public void parse() {
		String queryString = "name=alice&age=20&name=bob&age=30&page=1\r\n";
		QueryStringDecoder decoder = new QueryStringDecoder();

		MultiValueMap<String, String> parameters = decoder.parseQueryString(queryString);

		Assertions.assertThat(parameters)
			.containsEntry("name", List.of("alice", "bob"))
			.containsEntry("age", List.of("20", "30"))
			.containsEntry("page", List.of("1"));
	}

	@Test
	public void noneValue() {
		String queryString = "\r\n";
		QueryStringDecoder decoder = new QueryStringDecoder();

		MultiValueMap<String, String> parameters = decoder.parseQueryString(queryString);
		Assertions.assertThat(parameters)
			.isEmpty();
	}
}