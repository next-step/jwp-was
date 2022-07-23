package webserver.http.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.ContentType;

public class HttpResponseHeadersTest {
	@Test
	@DisplayName("ResponseHeaders String 변환 테스트")
	public void stringToResponseHeaders() {
		HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders();
		httpResponseHeaders.putContentLength(100);
		httpResponseHeaders.putContentType(ContentType.HTML);

		StringBuffer buffer = new StringBuffer();
		buffer.append("Content-Length: 100\r\n");
		buffer.append("Content-Type: text/html;charset=utf-8\r\n");
		assertThat(httpResponseHeaders.getResponseHeaders()).isEqualTo(buffer.toString());
	}
}
