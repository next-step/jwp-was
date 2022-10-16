package webserver.http;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultRequestTest {
	HttpMessageGenerator messageGenerator;

	@BeforeEach
	void setUp() throws IOException {
		messageGenerator = new HttpMessageGenerator();
	}

	@DisplayName("When Request is a POST type, queryString can be parsed.")
	@Test
	void whenPostTypeQueryStringShouldParsed() throws IOException {
		DefaultRequest request = (DefaultRequest)messageGenerator.generatePostRequest();

		assertThat(request.isFormRequest()).isTrue();
		assertThat(request.getParameter("key")).isEqualTo("value");
		assertThat(request.getParameter("page")).isEqualTo("1");
	}

	@DisplayName("When Request is a GET type, queryString can be parsed.")
	@Test
	void whenGetTypeQueryStringShouldParsed() throws IOException {
		DefaultRequest request = (DefaultRequest)messageGenerator.generateGetRequest();

		assertThat(request.isFormRequest()).isFalse();
		assertThat(request.getParameter("key")).isEqualTo("value");
		assertThat(request.getParameter("page")).isEqualTo("1");
	}

	@Test
	void parseCookies() throws IOException {
		DefaultRequest request = (DefaultRequest)messageGenerator.generateGetRequest();

		assertThat(request.getCookies())
			.containsValue(new Cookie("JSESSIONID", "50f59aa2-3d3b-451c-9aaa-9b5d5d5db6d5"))
			.containsValue(new Cookie("utma", "9384732"));
	}

	@Test
	void parseSession() throws IOException {
		String sessionId = "50f59aa2-3d3b-451c-9aaa-9b5d5d5db6d5";
		DefaultRequest request = (DefaultRequest)messageGenerator.generateGetRequest();

		HttpSession session = request.getSession();
		assertThat(session.hasChanged()).isTrue();
		assertThat(session.getSessionId()).isEqualTo(sessionId);
	}

	@Test
	void parseFirstLine() throws IOException {
		DefaultRequest request = (DefaultRequest)messageGenerator.generateGetRequest();

		assertThat(request.getRequestUri()).isEqualTo("/hello/index.html");
		assertThat(request.getServletPath()).isEqualTo("/hello");
		assertThat(request.getQueryString()).isEqualTo("key=value&page=1");
	}
}