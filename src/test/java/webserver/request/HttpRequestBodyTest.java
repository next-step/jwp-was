package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestBodyTest {

    @DisplayName("쿼리스트링 형식의 바디를 파싱한다.")
    @Test
    void createWithQueryStrings() {
        HttpRequestBody requestBody = HttpRequestBody.of("test1=test1&test2=test2");

        assertThat(requestBody.map().get("test1")).isEqualTo("test1");
        assertThat(requestBody.map().get("test2")).isEqualTo("test2");
    }

    @Test
    void createWithEmptyTest() {
        HttpRequestBody httpBody = HttpRequestBody.createEmpty();

        assertThat(httpBody.map()).isEmpty();
    }

    @Test
    void createWithNullTest() {
        assertThatThrownBy(
            () -> HttpRequestBody.of(null)
        ).isInstanceOf(NullPointerException.class);
    }

}
