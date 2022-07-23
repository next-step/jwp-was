package webserver.http.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.ContentType;

public class ResourceTest {

	@Test
	@DisplayName("리소스 읽기 실패")
	public void notFoundResource() {
		Assertions.assertThrows(NullPointerException.class, () -> Resource.of("/templates2/index.html"));
	}

	@Test
	@DisplayName("리소스 읽기")
	public void readResource() {
		Resource resource = Resource.of("./templates/index.html");

		assertThat(resource.getContentType()).isEqualTo(ContentType.HTML);
		assertThat(resource.getContentLength()).isEqualTo(6902);
	}
}
