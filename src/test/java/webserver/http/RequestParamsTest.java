package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParamsTest {
	@DisplayName("RequestParas 생성 후 값 조회 테스트")
	@Test
	void value_test() {
		RequestParams params = new RequestParams("userId=javajigi", "password=password&name=JaeSung");
		assertThat(params.value("userId")).isEqualTo("javajigi");
		assertThat(params.value("password")).isEqualTo("password");
		assertThat(params.value("name")).isEqualTo("JaeSung");
	}
}
