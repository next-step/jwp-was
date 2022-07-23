package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ContentTypeTest {
	@Test
	@DisplayName("URI로 ContentType 찾기")
	public void findContentType() {
		ContentType contentType = ContentType.findContentType("/index.html");

		assertThat(contentType).isEqualTo(ContentType.HTML);
	}

	@Test
	@DisplayName("지원하지 않는 ContentType")
	public void notSupportedContentType() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> ContentType.findContentType("/test.xml"));
	}
}
