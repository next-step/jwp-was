package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineTest {

	@DisplayName("HTTP GET 요청의 첫 번째 라인을 파싱한다.")
	@Test
	void parseHttpGetRequest() {
		RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");
		assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
		assertThat(requestLine.getPath()).isEqualTo("/users");
		assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
		assertThat(requestLine.getVersion()).isEqualTo("1.1");
	}
}
