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
}
