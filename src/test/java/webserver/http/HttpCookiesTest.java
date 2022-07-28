package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpCookiesTest {
	@Test
	@DisplayName("쿠키 생성 테스트")
	public void create() {
		HttpCookies httpCookies = HttpCookies.of("logined=true; Path=/");

		assertThat(httpCookies.getCookie("logined")).isEqualTo("true");
		assertThat(httpCookies.getCookie("Path")).isEqualTo("/");
		assertThat(httpCookies.getFlatCookies()).isEqualTo("logined=true; Path=/");
	}
}
