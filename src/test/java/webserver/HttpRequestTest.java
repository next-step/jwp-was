package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import webserver.http.BufferedReaderToHttpRequest;
import webserver.http.HttpRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
	private String testDirectory = "./src/test/resources/";

	@DisplayName("GET 요청 테스트")
	@Test
	void request_GET() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
		BufferedReaderToHttpRequest bufferedReaderToHttpRequest = new BufferedReaderToHttpRequest(in);
		HttpRequest httpRequest = bufferedReaderToHttpRequest.parsed();

		assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
		assertThat(httpRequest.getPath()).isEqualTo("/user/create");
		assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
		assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
	}

	@DisplayName("POST 요청 테스트")
	@Test
	public void request_POST() throws Exception {
		InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
		BufferedReaderToHttpRequest bufferedReaderToHttpRequest = new BufferedReaderToHttpRequest(in);
		HttpRequest httpRequest = bufferedReaderToHttpRequest.parsed();

		assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);
		assertThat(httpRequest.getPath()).isEqualTo("/user/create");
		assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
		assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
	}
}
