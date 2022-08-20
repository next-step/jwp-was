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
		HttpPath httpPath = new HttpPath(path);

	    // then
		assertAll(
			() -> assertThat(httpPath.getPath()).isEqualTo("/users"),
			() -> assertThat(httpPath.getRequestParameters().getRequestParameters()).hasSize(3),
			() -> assertThat(httpPath.getRequestParameters().getRequestParameters().get("userId")).isEqualTo("javajigi"),
			() -> assertThat(httpPath.getRequestParameters().getRequestParameters().get("password")).isEqualTo("password"),
			() -> assertThat(httpPath.getRequestParameters().getRequestParameters().get("name")).isEqualTo("JaeSung")
		);
	}
}