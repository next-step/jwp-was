package webserver.http;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.netty.buffer.Unpooled;

class HttpRequestDecoderTest {
	String get = "GET /hello.html?key=value&page=1 HTTP/1.1\r\n"
		+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
		+ "Host: www.tutorialspoint.com\r\n"
		+ "Cookie: JSESSIONID=50f59aa2-3d3b-451c-9aaa-9b5d5d5db6d5; utma=9384732\r\n"
		+ "Accept-Language: en-us\r\n"
		+ "Accept-Encoding: gzip, deflate\r\n"
		+ "Connection: Keep-Alive\r\n"
		+ "\r\n";

	String post = "POST /hello.html HTTP/1.1\r\n"
		+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
		+ "Host: www.tutorialspoint.com\r\n"
		+ "Accept-Language: en-us\r\n"
		+ "Accept-Encoding: gzip, deflate\r\n"
		+ "Connection: Keep-Alive\r\n"
		+ "Content-Type: application/x-www-form-urlencoded\r\n"
		+ "Content-Length: 27\r\n"
		+ "\r\n"
		+ "key=value&page=1&name=abc\r\n";

	public static HttpRequestDecoder requestDecoder;

	@BeforeAll
	public static void setUp() throws IOException, URISyntaxException {
		requestDecoder = new HttpRequestDecoder();
	}

	@Test
	void ows() {
		String expectedMessage = "Invalid separator. only a single space or horizontal tab allowed.";
		Class expectedException = IllegalArgumentException.class;

		assertThatThrownBy(() -> {
			requestDecoder.decodeFirstLine(
				Unpooled.wrappedBuffer("POST  /hello.html?key=value&page=1 HTTP/1.1\r\n".getBytes()));
		}).isInstanceOf(expectedException)
			.hasMessage(expectedMessage);
	}

	@Test
	void owsw() {
		String expectedMessage = "Invalid argument count exception. There must be 3 elements, but received";
		Class expectedException = IllegalArgumentException.class;

		assertThatThrownBy(() -> {
			requestDecoder.decodeFirstLine(
				Unpooled.wrappedBuffer("POST/hello.html?key=value&page=1 HTTP/1.1\r\n".getBytes()));
		}).isInstanceOf(expectedException)
			.hasMessage(expectedMessage);
	}

	@Test
	void decodeRequestLine() {
		RequestLine requestLine = requestDecoder.decodeFirstLine(
			Unpooled.wrappedBuffer("GET /hello.html?key=value&page=1 HTTP/1.1\r\n".getBytes()));

		assertThat(requestLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
		assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
		assertThat(requestLine.getUri().getPath()).isEqualTo("/hello.html");
		assertThat(requestLine.getUri().getQuery()).isEqualTo("key=value&page=1");
	}

	@Test
	void decodeGetHeaders() throws IOException {
		HttpRequest request = requestDecoder
			.decode(new ByteArrayInputStream(get.getBytes()));

		assertThat(request.getHeader("User-Agent")).isEqualTo("Mozilla/4.0 (compatible; MSIE5.01; Windows NT)");
		assertThat(request.getHeader("Host")).isEqualTo("www.tutorialspoint.com");
		assertThat(request.getHeader("Cookie")).isEqualTo(
			"JSESSIONID=50f59aa2-3d3b-451c-9aaa-9b5d5d5db6d5; utma=9384732");
		assertThat(request.getHeader("Accept-Language")).isEqualTo("en-us");
		assertThat(request.getHeader("Accept-Encoding")).isEqualTo("gzip, deflate");
		assertThat(request.getHeader("Connection")).isEqualTo("Keep-Alive");
	}

	@Test
	void form() throws IOException {
		HttpRequest request = requestDecoder
			.decode(new ByteArrayInputStream(post.getBytes()));

		assertThat(request.getParameter("key")).isEqualTo("value");
		assertThat(request.getParameter("page")).isEqualTo("1");
	}

	@Test
	void getCookie() throws IOException {
		HttpRequest request = requestDecoder
			.decode(new ByteArrayInputStream(get.getBytes()));

		assertThat(request.getCookies())
			.containsValue(new Cookie("JSESSIONID", "50f59aa2-3d3b-451c-9aaa-9b5d5d5db6d5"))
			.containsValue(new Cookie("utma", "9384732"));
	}

	@Test
	void getSession() throws IOException {
		String sessionId = "50f59aa2-3d3b-451c-9aaa-9b5d5d5db6d5";
		HttpRequest request = requestDecoder
			.decode(new ByteArrayInputStream(get.getBytes()));

		HttpSession session = request.getSession();
		assertThat(session.hasChanged()).isTrue();
		assertThat(session.getSessionId()).isEqualTo(sessionId);
	}
}