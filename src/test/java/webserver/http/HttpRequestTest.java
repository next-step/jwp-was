package webserver.http;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
	private final String testDirectory = "./src/test/resources/";
	private HttpRequest httpRequest;

	@AfterEach
	private void validateUserCreateRequest() {
		assertThat(httpRequest.getPath()).isEqualTo("/user/create");
		assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive");
		assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi");
	}

	@ParameterizedTest(name = "{0} 요청 테스트")
	@ValueSource(strings = {"GET", "POST"})
	public void request(String httpMethod) throws Exception {
		InputStream in = new FileInputStream(testFileName(httpMethod));
		BufferedReaderToHttpRequest bufferedReaderToHttpRequest = new BufferedReaderToHttpRequest(in);
		httpRequest = bufferedReaderToHttpRequest.parsed();

		assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.valueOf(httpMethod));
	}

	private String testFileName(String httpMethod) {
		if (httpMethod.equals("GET")) {
			return testDirectory + "Http_GET.txt";
		}
		return testDirectory + "Http_POST.txt";
	}
}
