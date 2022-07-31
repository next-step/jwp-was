package webserver.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UriTest {

    @DisplayName("Uri 중 path 를 파싱한다.")
    @Test
    void createOnlySlashTest() {
        Uri uri = Uri.of("/");

        assertThat(uri.getPath()).isEqualTo("/");
    }

    @Test
    void createEmptyPathFailTest() {
        assertThatThrownBy(
            () -> Uri.of("")
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createNullFailTest() {
        assertThatThrownBy(
            () -> Uri.of(null)
        ).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("Uri 중 queryString 을 파싱한다.")
    @Test
    void createQueryStringsTest() {
        Uri uri
            = Uri.of("/user/create?userId=testId&password=testPw&name=testName&email=testEmail@email.com");

        assertThat(uri.getPath()).isEqualTo("/user/create");
        assertThat(uri.sizeOfParams()).isEqualTo(4);
        assertThat(uri.getParameter("userId")).isEqualTo("testId");
        assertThat(uri.getParameter("password")).isEqualTo("testPw");
        assertThat(uri.getParameter("name")).isEqualTo("testName");
        assertThat(uri.getParameter("email")).isEqualTo("testEmail@email.com");
    }

}
