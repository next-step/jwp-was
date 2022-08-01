package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpCookiesTest {
	private final HttpCookies httpCookies = new HttpCookies();

	@BeforeEach
	public void init() {
		httpCookies.addCookie(new HttpCookie("logined", "true"));
		httpCookies.addCookie("SessionId", "123", "/");
		httpCookies.addCookie("test", "test", "/test");
	}

	@Test
	@DisplayName("쿠키 추가 테스트")
	public void addCookie() {
		List<HttpCookie> cookies = httpCookies.getCookies();

		assertThat(cookies.size()).isEqualTo(3);
	}

	@Test
	@DisplayName("쿠키 name 기준 찾기 테스트")
	public void find() {
		assertThat(httpCookies.find("logined").getValue()).isEqualTo("true");
		assertThat(httpCookies.find("SessionId").getValue()).isEqualTo("123");
		assertThat(httpCookies.find("test").getValue()).isEqualTo("test");
		assertThat(httpCookies.find("test").getPath()).isEqualTo("/test");
	}
}
