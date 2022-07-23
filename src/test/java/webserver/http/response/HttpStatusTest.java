package webserver.http.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpStatusTest {

	@Test
	@DisplayName("생성 테스트")
	public void create() {
		assertThat(HttpStatus.OK).isEqualTo(HttpStatus.of("200"));
		assertThat(HttpStatus.FOUND).isEqualTo(HttpStatus.of("302"));
		assertThat(HttpStatus.NOT_FOUND).isEqualTo(HttpStatus.of("404"));
	}

	@Test
	@DisplayName("지원하지 않는 HttpStatus")
	public void notSupportedHttpStatus() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> HttpStatus.of("1000"));
	}
}
