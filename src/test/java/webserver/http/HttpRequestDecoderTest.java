package webserver.http;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;

import io.netty.buffer.Unpooled;

class HttpRequestDecoderTest {
	String get = "GET /hello.html?key=value&page=1 HTTP/1.1\r\n"
		+ "User-Agent : Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
		+ "Host: www.tutorialspoint.com\r\n"
		+ "Accept-Language: en-us\r\n"
		+ "Accept-Encoding: gzip, deflate\r\n"
		+ "Connection: Keep-Alive\r\n"
		+ "\r\n";

	String post = "POST /hello.html?key=value&page=1 HTTP/1.1\r\n"
		+ "User-Agent : Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
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
	public void ows(){
		assertThatThrownBy(() -> {
			requestDecoder.decodeFirstLine(Unpooled.wrappedBuffer("POST  /hello.html?key=value&page=1 HTTP/1.1\r\n".getBytes()));
		}).isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Invalid separator. only a single space or horizontal tab allowed.");
	}

	@Test
	public void owsw(){
		assertThatThrownBy(() -> {
			requestDecoder.decodeFirstLine(Unpooled.wrappedBuffer("POST/hello.html?key=value&page=1 HTTP/1.1\r\n".getBytes()));
		}).isInstanceOf(IllegalArgumentException.class)
			.hasMessageStartingWith("Invalid argument count exception. There must be 3 elements, but received");
	}


	@Test
	public void decodeRequestLine() {
		RequestLine requestLine = requestDecoder.decodeFirstLine(Unpooled.wrappedBuffer("GET /hello.html?key=value&page=1 HTTP/1.1\r\n".getBytes()));

		assertThat(requestLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);
		assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
		assertThat(requestLine.getUri().getPath()).isEqualTo("/hello.html");
		assertThat(requestLine.getUri().getQuery()).isEqualTo("key=value&page=1");
	}

	@Test
	public void decodeGetHeaders(){
		MultiValueMap<String, String> headers = requestDecoder.decodeHeaders(Unpooled.wrappedBuffer(get.getBytes()));

		assertThat(headers)
			.containsEntry("User-Agent ", List.of("Mozilla/4.0 (compatible; MSIE5.01; Windows NT)"))
			.containsEntry("Host", List.of("www.tutorialspoint.com"))
			.containsEntry("Accept-Language", List.of("en-us"))
			.containsEntry("Accept-Encoding", List.of("gzip, deflate"))
			.containsEntry("Connection", List.of("Keep-Alive"));
	}

	@Test
	public void decodePostHeaders(){
		MultiValueMap<String, String> headers = requestDecoder.decodeHeaders(Unpooled.wrappedBuffer(post.getBytes()));

		assertThat(headers)
			.containsEntry("User-Agent ", List.of("Mozilla/4.0 (compatible; MSIE5.01; Windows NT)"))
			.containsEntry("Host", List.of("www.tutorialspoint.com"))
			.containsEntry("Accept-Language", List.of("en-us"))
			.containsEntry("Accept-Encoding", List.of("gzip, deflate"))
			.containsEntry("Connection", List.of("Keep-Alive"));
	}

	@Test
	public void form() {
		HttpRequest httpRequest = requestDecoder.decode(
			Unpooled.wrappedBuffer(post.getBytes()));

		assertThat(httpRequest.getQueryParams())
			.containsEntry("key", List.of("value"))
			.containsEntry("page", List.of("1"));
	}
}