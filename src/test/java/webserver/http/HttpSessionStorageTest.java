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
}
