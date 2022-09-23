package webserver.http;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

public class HttpResponseTest {
	@Test
	void sendRedirectTest() throws IOException {
		String expected = "HTTP/1.1 302 Found\r\n" +
			"Location: /index.html\r\n" +
			"\r\n";
		String location = "/index.html";

		OutputStream outputStream = new ByteArrayOutputStream();
		HttpResponse httpResponse = new HttpResponse(outputStream);
		httpResponse.sendRedirect(location);

		assertThat(httpResponse.toEncoded()).isEqualTo(expected);
	}
	// forward
	// 조정되는 위치에 해당하는 서블릿을 찾는다.
	// 요청의 path를 변경한다. 응답을 초기화 한다(쿠키는 그대로둔다).
	// 해당 서블릿에 요청과 응답객체를 전달한다.
}
