package webserver.http.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpProtocolTest {
	@Test
	@DisplayName("버전 정보 없는 Protocol 파싱")
	public void parseNoVersionOfProtocol() {
		// given
		String line = "HTTP/";

		// when & then
		Assertions.assertThrows(IllegalArgumentException.class, () -> HttpProtocol.of(line));
	}

	@Test
	@DisplayName("Protocol 파싱")
	public void parseProtocol() {
		// given
		String line = "HTTP/1.1";

		// when
		HttpProtocol httpProtocol = HttpProtocol.of(line);

		// then
		assertThat(httpProtocol.getProtocol()).isEqualTo("HTTP");
		assertThat(httpProtocol.getVersion()).isEqualTo("1.1");
	}
}
