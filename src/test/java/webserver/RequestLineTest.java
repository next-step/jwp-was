package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import webserver.request.enums.HttpMethod;
import webserver.request.RequestLine;

/**
 * Created by hspark on 2019-08-01.
 */
class RequestLineTest {

	@Test
	void test_requestLine_생성_GET() {
		String requestLineStr = "GET /users HTTP/1.1";

		RequestLine requestLine = RequestLine.parse(requestLineStr);

		Assertions.assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
		Assertions.assertThat(requestLine.getRequestUrl()).isEqualTo("/users");
		Assertions.assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
	}

	@Test
	void test_requestLine_생성_POST() {
		String requestLineStr = "POST /users HTTP/1.1";

		RequestLine requestLine = RequestLine.parse(requestLineStr);

		Assertions.assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.POST);
		Assertions.assertThat(requestLine.getRequestUrl()).isEqualTo("/users");
		Assertions.assertThat(requestLine.getHttpVersion()).isEqualTo("HTTP/1.1");
	}
}