package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpMethodTest {

	@Test
	@DisplayName("지원하지 않는 메서드")
	public void notSupportedMethod() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> HttpMethod.of("PUT"));
	}

	@Test
	@DisplayName("지원하는 메서드")
	public void supportedMethod() {
		assertThat(HttpMethod.of("GET")).isEqualTo(HttpMethod.of("GET"));
		assertThat(HttpMethod.of("POST")).isEqualTo(HttpMethod.of("POST"));
	}
}
