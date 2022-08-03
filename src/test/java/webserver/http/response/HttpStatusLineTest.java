package webserver.http.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.HttpProtocol;

public class HttpStatusLineTest {

	@Test
	@DisplayName("HttpStatusLine String 변환 테스트")
	public void getHttpStatusLine() {
		HttpStatusLine httpStatusLine = new HttpStatusLine(HttpProtocol.of("HTTP/1.1"));

		assertThat(httpStatusLine.getHttpStatusLine(HttpStatus.OK)).isEqualTo("HTTP/1.1 200 OK\r\n");
		assertThat(httpStatusLine.getHttpStatusLine(HttpStatus.FOUND)).isEqualTo("HTTP/1.1 302 Found\r\n");
		assertThat(httpStatusLine.getHttpStatusLine(HttpStatus.NOT_FOUND)).isEqualTo("HTTP/1.1 404 Not Found\r\n");
		assertThat(httpStatusLine.getHttpStatusLine(HttpStatus.INTERNAL_SERVER_ERROR)).isEqualTo("HTTP/1.1 500 Internal Server Error\r\n");
	}
}
