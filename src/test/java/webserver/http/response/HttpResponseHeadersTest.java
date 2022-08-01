package webserver.http.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.ContentType;
import webserver.http.HttpCookie;

public class HttpResponseHeadersTest {

	ByteArrayOutputStream out = new ByteArrayOutputStream();

	@Test
	@DisplayName("write 테스트")
	public void write() throws IOException {
		HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders();
		httpResponseHeaders.addContentLength(100);
		httpResponseHeaders.addContentType(ContentType.HTML);

		httpResponseHeaders.write(out);

		StringBuffer buffer = new StringBuffer();
		buffer.append("Content-Length: 100\r\n");
		buffer.append("Content-Type: text/html;charset=utf-8\r\n");
		assertThat(new String(out.toByteArray())).isEqualTo(buffer.toString());
	}

	@Test
	@DisplayName("쿠키 포함 write 테스트")
	public void writeWithCookie() throws IOException {
		HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders();
		httpResponseHeaders.addAttribute("Location", "/index.html");
		httpResponseHeaders.addCookie(new HttpCookie("logined", "true"));

		httpResponseHeaders.write(out);

		StringBuffer buffer = new StringBuffer();
		buffer.append("Location: /index.html\r\n");
		buffer.append("Set-Cookie: logined=true; path=/;\r\n");
		assertThat(new String(out.toByteArray())).isEqualTo(buffer.toString());
	}
}
