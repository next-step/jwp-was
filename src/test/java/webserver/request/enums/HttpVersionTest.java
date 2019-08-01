package webserver.request.enums;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by hspark on 2019-08-01.
 */
class HttpVersionTest {

	@Test
	void test_findHttpVersion() {
		String httpVersionStr = "HTTP/1.1";
		HttpVersion httpVersion = HttpVersion.findByHeaderValue(httpVersionStr);
		Assertions.assertThat(httpVersion).isEqualTo(HttpVersion.HTTP_1_1);
	}

	@Test
	void test_잘못된_값() {
		String httpVersionStr = "1";
		Assertions.assertThatIllegalArgumentException().isThrownBy(() -> HttpVersion.findByHeaderValue(httpVersionStr));
	}
}