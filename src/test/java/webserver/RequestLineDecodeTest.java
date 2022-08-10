package webserver;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.netty.buffer.Unpooled;

public class RequestLineDecodeTest {
	static RequestLineDecoder requestLineDecoder;
	@BeforeAll
	public static void setUp() {
		requestLineDecoder = new RequestLineDecoder();
	}
	@Test
	public void decodeRequestLine() {
		byte[] requestLine = "GET /hello?page=1&category=admin HTTP/1.1\r\n".getBytes(StandardCharsets.UTF_8);

		RequestLine httpRequestLine = requestLineDecoder.decode(Unpooled.wrappedBuffer(requestLine));

		Assertions.assertEquals("GET", httpRequestLine.getMethod());
		Assertions.assertEquals(HttpVersion.HTTP_1_1, httpRequestLine.getHttpVersion());
		Assertions.assertEquals("/hello", httpRequestLine.getUri().getPath());
		Assertions.assertEquals("admin", httpRequestLine.getUri().getParams().get("category"));
	}


	@Test
	void when_lessThanThreeElements_thenRequestLineFormatExceptionThrown(){
		byte[] requestLine = "GET /hello\r\n".getBytes(StandardCharsets.UTF_8);

		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			RequestLine httpRequestLine = requestLineDecoder.decode(Unpooled.wrappedBuffer(requestLine));
		});

		String expectedMessage = "Invalid argument count exception. There must be 3 elements";
		String actualMessage = exception.getMessage();

		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void when_moreThanOneSeparator_thenRequestLineFormatExceptionThrown(){
		byte[] requestLine = "GET  /hello HTTP/1.1\r\n".getBytes(StandardCharsets.UTF_8);

		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			RequestLine httpRequestLine = requestLineDecoder.decode(Unpooled.wrappedBuffer(requestLine));
		});

		String expectedMessage = "Invalid separator. only a single space or horizontal tab allowed.";
		String actualMessage = exception.getMessage();

		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void when_wrongUriForamt_thenUriFormatExceptionThrown(){
		byte[] requestLine = "GET hello HTTP/1.1\r\n".getBytes(StandardCharsets.UTF_8);

		Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			RequestLine httpRequestLine = requestLineDecoder.decode(Unpooled.wrappedBuffer(requestLine));
		});

		String expectedMessage = "Unsupported URI format. URI must start with '/'";
		String actualMessage = exception.getMessage();

		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}
}