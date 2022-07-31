package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpSessionTest {

	@Test
	@DisplayName("생성 테스트")
	public void create() {
		Session httpSession = new HttpSession();

		assertThat(httpSession.getId()).isNotEmpty();
	}

	@Test
	@DisplayName("Get/Set Attribute 테스트")
	public void attribute() {
		Session httpSession = new HttpSession();

		httpSession.setAttribute("JSESSIONID", "123");

		assertThat(httpSession.getAttribute("JSESSIONID")).isEqualTo("123");
	}

	@Test
	@DisplayName("remove Attribute 테스트")
	public void removeAttribute() {
		Session httpSession = new HttpSession();
		httpSession.setAttribute("JSESSIONID", "123");

		httpSession.removeAttribute("JSESSIONID");

		assertThat(httpSession.getAttribute("JSESSIONID")).isNull();
	}

	@Test
	@DisplayName("invalid 테스트")
	public void invalid() {
		Session httpSession = new HttpSession();
		httpSession.setAttribute("JSESSIONID", "123");
		httpSession.setAttribute("TEST", "123");

		httpSession.invalidate();

		assertThat(httpSession.getAttribute("JSESSIONID")).isNull();
		assertThat(httpSession.getAttribute("TEST")).isNull();
	}

}
