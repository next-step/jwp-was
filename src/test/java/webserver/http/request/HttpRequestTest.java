package webserver.http.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

public class HttpRequestTest {
	private final String testDirectory = "./src/test/resources/";

	@Test
	public void request_GET() throws Exception {
		InputStream in = new FileInputStream(testDirectory + "http/Http_GET.txt");
		HttpRequest request = HttpRequestParser.parse(in);

		assertThat(request.getMethod().toString()).isEqualTo("GET");
		assertThat(request.getPath()).isEqualTo("/user/create");
		assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
		assertThat(request.getParameter("userId")).isEqualTo("javajigi");
	}

	@Test
	public void request_POST() throws Exception {
		InputStream in = new FileInputStream(testDirectory + "http/Http_POST.txt");
		HttpRequest request = HttpRequestParser.parse(in);

		assertThat(request.getMethod().toString()).isEqualTo("POST");
		assertThat(request.getPath()).isEqualTo("/user/create");
		assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
		assertThat(request.getParameter("userId")).isEqualTo("javajigi");
	}


	@Test
	public void request_POST2() throws Exception {
		InputStream in = new FileInputStream(testDirectory + "http/Http_POST2.txt");
		HttpRequest request = HttpRequestParser.parse(in);

		assertThat(request.getMethod().toString()).isEqualTo("POST");
		assertThat(request.getPath()).isEqualTo("/user/create");
		assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
		assertThat(request.getParameter("id")).isEqualTo("1");
		assertThat(request.getParameter("userId")).isEqualTo("javajigi");
	}
}
