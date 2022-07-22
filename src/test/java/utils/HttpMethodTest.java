package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpMethodTest {
	@DisplayName("HttpMethod에 있는 GET과 POST가 요청되는 경우, True를 반환한다.")
	@Test
	void matchedHttpMethod() {
		// given
		String method = "GET";

		// when
		boolean result = HttpMethod.matchedPropertyOf(method);

		// then
		assertThat(result).isTrue();
	}

	@DisplayName("HttpMethod에 있는 값이 아닌 경우, False를 반환한다.")
	@Test
	void notMatchedHttpMethod() {
		// given
		String method = "GAT";

		// when
		boolean result = HttpMethod.matchedPropertyOf(method);

		// then
		assertThat(result).isFalse();
	}
}