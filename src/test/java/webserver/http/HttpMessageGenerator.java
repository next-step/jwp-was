package webserver.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.util.MultiValueMap;

import io.netty.buffer.Unpooled;

public class HttpMessageGenerator {
	private String get = "GET /hello/index.html?key=value&page=1 HTTP/1.1\r\n"
		+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
		+ "Host: www.tutorialspoint.com\r\n"
		+ "Cookie: JSESSIONID=50f59aa2-3d3b-451c-9aaa-9b5d5d5db6d5; utma=9384732\r\n"
		+ "Accept-Language: en-us\r\n"
		+ "Accept-Encoding: gzip, deflate\r\n"
		+ "Connection: Keep-Alive\r\n"
		+ "\r\n";

	private String post = "POST /hello.html HTTP/1.1\r\n"
		+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n"
		+ "Host: www.tutorialspoint.com\r\n"
		+ "Accept-Language: en-us\r\n"
		+ "Accept-Encoding: gzip, deflate\r\n"
		+ "Connection: Keep-Alive\r\n"
		+ "Content-Type: application/x-www-form-urlencoded\r\n"
		+ "Content-Length: 27\r\n"
		+ "\r\n"
		+ "key=value&page=1&name=abc\r\n";

	HttpRequest generateGetRequest() throws IOException {
		HttpRequestDecoder requestDecoder = new HttpRequestDecoder();
		return requestDecoder.decode(new ByteArrayInputStream(get.getBytes()));
	}

	HttpRequest generatePostRequest() throws IOException {
		HttpRequestDecoder requestDecoder = new HttpRequestDecoder();
		return requestDecoder.decode(new ByteArrayInputStream(post.getBytes()));
	}

	RequestLine generateRequestLine(String requestLine) {
		HttpRequestDecoder requestDecoder = new HttpRequestDecoder();
		return requestDecoder.decodeFirstLine(
			Unpooled.wrappedBuffer(requestLine.getBytes()));
	}

	MultiValueMap<String, String> generateHeaders(){
		HttpRequestDecoder requestDecoder = new HttpRequestDecoder();
		return requestDecoder.decodeHeaders(Unpooled.wrappedBuffer(get.getBytes()));
	}
}
