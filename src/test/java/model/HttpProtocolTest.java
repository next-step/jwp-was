package model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpProtocolTest {
	@DisplayName("프로토콜을 파싱이 정상적으로 되었습니다.")
	@Test
	void parsingProtocol() {
	    // given
		String protocol = "HTTP/1.1";

	    // when
		HttpProtocol httpProtocol = new HttpProtocol(protocol);

	    // then
		assertAll(
			() -> assertThat(httpProtocol.getProtocol()).isEqualTo("HTTP"),
			() -> assertThat(httpProtocol.getVersion()).isEqualTo("1.1")
		);
	}
}