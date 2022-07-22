package model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpPathTest {
	@DisplayName("HTTP 요청에 대한 path를 정상적으로 파싱했다.")
	@Test
	void parsingHttpPath() {
	    // given
	    String path = "/users?userId=javajigi&password=password&name=JaeSung";

	    // when
		HttpPath httpPath = HttpPath.of(path);

	    // then
		assertAll(
			() -> assertThat(httpPath.getPath()).isEqualTo("/users"),
			() -> assertThat(httpPath.getQueryString().getQueryStringOfPath()).hasSize(3),
			() -> assertThat(httpPath.getQueryString().getQueryStringOfPath().get("userId")).isEqualTo("javajigi"),
			() -> assertThat(httpPath.getQueryString().getQueryStringOfPath().get("password")).isEqualTo("password"),
			() -> assertThat(httpPath.getQueryString().getQueryStringOfPath().get("name")).isEqualTo("JaeSung")
		);
	}
}