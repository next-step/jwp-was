package webserver.http.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.HttpProtocol;

public class HttpStatusLineTest {
	@Test
	@DisplayName("생성 테스트")
	public void create() {
		assertThat(new HttpStatusLine(HttpProtocol.of("HTTP/1.1"), HttpStatus.OK)).isEqualTo(new HttpStatusLine(HttpProtocol.of("HTTP/1.1"), HttpStatus.OK));
	}

	@Test
	@DisplayName("HttpStatusLine String 변환 테스트")
	public void getHttpStatusLine() {
		HttpStatusLine httpStatusLine = new HttpStatusLine(HttpProtocol.of("HTTP/1.1"), HttpStatus.OK);

		assertThat(httpStatusLine.getHttpStatusLine()).isEqualTo("HTTP 200 OK");
	}
}
