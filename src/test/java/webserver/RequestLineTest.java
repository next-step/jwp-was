package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import exception.IllegalRequestLineException;

class RequestLineTest {

	@DisplayName("요구사항 1 - HTTP GET 요청의 첫 번째 라인을 파싱한다.")
	@Test
	void parseHttpGetRequest() {
		RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");

		assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
		assertThat(requestLine.getRequestPath()).isEqualTo("/users");
		assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
		assertThat(requestLine.getVersion()).isEqualTo("1.1");
	}

	@DisplayName("요구사항 2 - HTTP POST 요청의 첫 번째 라인을 파싱한다.")
	@Test
	void parseHttpPostRequest() {
		RequestLine requestLine = new RequestLine("POST /users HTTP/1.1");

		assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
		assertThat(requestLine.getRequestPath()).isEqualTo("/users");
		assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
		assertThat(requestLine.getVersion()).isEqualTo("1.1");
	}

	@DisplayName("요구사항 3 - Query String을 파싱한다.")
	@Test
	void parseQueryString() {
		RequestLine requestLine = new RequestLine("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");
		String queryString = requestLine.getQueryString();
		String userId = requestLine.getParameter("userId");
		String password = requestLine.getParameter("password");
		String name = requestLine.getParameter("name");

		assertThat(queryString).isEqualTo("userId=javajigi&password=password&name=JaeSung");
		assertThat(userId).isEqualTo("javajigi");
		assertThat(password).isEqualTo("password");
		assertThat(name).isEqualTo("JaeSung");
	}

	@DisplayName("HTTP 요청의 request line이 유효하지 않으면 IllegalRequestLineException 예외가 발생한다.")
	@ValueSource(strings = {"/users HTTP/1.1", "GET HTTP/1.1", "GET /users"})
	@ParameterizedTest
	void illegalRequestLine(String requestLine) {
		assertThatThrownBy(() -> new RequestLine(requestLine))
			.isInstanceOf(IllegalRequestLineException.class);
	}
}
