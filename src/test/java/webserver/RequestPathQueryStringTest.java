package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class RequestPathQueryStringTest {

	@ParameterizedTest(name = "요청의 값이 null 또는 비어있는 경우, 예외처리 된다.")
	@NullAndEmptySource
	void exceptionQueryStringIsNullOrEmpty(String queryString) {
		// when & then
		assertThatThrownBy(() -> new RequestParameters(queryString))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("Query String이 정상적으로 파싱되었습니다.")
	@Test
	void parsingQueryString() {
		// given
		String queryString = "userId=javajigi&password=password&name=JaeSung";

		// when
		RequestParameters requestParameters = new RequestParameters(queryString);

		// then
		Map<String, String> result = requestParameters.getRequestParameters();
		assertAll(
			() -> assertThat(result).hasSize(3),
			() -> assertThat(result.get("userId")).isEqualTo("javajigi"),
			() -> assertThat(result.get("password")).isEqualTo("password"),
			() -> assertThat(result.get("name")).isEqualTo("JaeSung")
		);
	}
}
