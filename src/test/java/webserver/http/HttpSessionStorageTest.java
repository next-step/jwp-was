package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpSessionStorageTest {

	HttpSessionStorage storage = HttpSessionStorage.getInstance();

	@Test
	@DisplayName("저장 테스트")
	public void save() {
		HttpSession httpSession = new HttpSession();
		storage.save(httpSession.getId(), httpSession);

		assertThat(storage.getSession(httpSession.getId())).isEqualTo(httpSession);
	}

	@Test
	@DisplayName("저장 유효성 테스트")
	public void validateSave() {
		HttpSession httpSession = new HttpSession();

		Assertions.assertThrows(IllegalArgumentException.class, () -> storage.save(httpSession.getId(), null));
	}

	@Test
	@DisplayName("세션 가져오기 테스트")
	public void getSession() {
		HttpSession httpSession = new HttpSession();
		HttpCookies httpCookies = new HttpCookies();
		httpCookies.addCookie(new HttpCookie(HttpSessionStorage.SESSION_ID, httpSession.getId()));
		storage.save(httpSession.getId(), httpSession);

		HttpSession session = HttpSessionStorage.getInstance().getSession(httpCookies);

		assertThat(session).isEqualTo(httpSession);
	}
}
