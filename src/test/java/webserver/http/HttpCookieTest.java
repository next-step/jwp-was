package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpCookieTest {
	@Test
	@DisplayName("쿠키 생성 테스트")
	public void create() {
		HttpCookie httpCookie = new HttpCookie("logined", "true");

		assertThat(httpCookie.getName()).isEqualTo("logined");
		assertThat(httpCookie.getValue()).isEqualTo("true");
		assertThat(httpCookie.getPath()).isEqualTo("/");
	}

	@Test
	@DisplayName("응답 시, 쿠키 추가하기 위한 스트링 변환 테스트")
	public void responseString() {
		HttpCookie httpCookie = new HttpCookie("logined", "true");

		assertThat(httpCookie.toResponseString()).isEqualTo("Set-Cookie: logined=true; path=/;");
	}
}
