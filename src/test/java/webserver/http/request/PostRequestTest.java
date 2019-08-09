package webserver.http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.cookie.Cookies;

import static org.assertj.core.api.Assertions.assertThat;
import static support.FileSupporter.read;

class PostRequestTest {

    private Request request;

    @BeforeEach
    void setUp() throws Exception {
        request = HttpRequest.of(read("POST_Request.txt"));
    }

    @DisplayName("쿠키를 가져온다.")
    @Test
    void getCookies() {
        // when
        final Cookies cookies = request.getCookies();
        final String value = cookies.getString("hello").get();

        // then
        assertThat(value).isEqualTo("world");
    }

    @DisplayName("쿠키가 없으면 값을 가져올 수 없다.")
    @Test
    void getCookiesNull() {
        // when
        final Cookies cookies = request.getCookies();
        final boolean exists = cookies.getString("sdfadadsf").isPresent();

        // then
        assertThat(exists).isFalse();
    }

    @DisplayName("경로를 가져온다.")
    @Test
    void getPath() {
        // when
        final String path = request.getPath();

        // then
        assertThat(path).isEqualTo("/post-test");
    }

    @DisplayName("헤더를 가져온다.")
    @Test
    void getHeader() {
        // when
        final String value = request.getHeader("HEADER_KEY").get();

        // then
        assertThat(value).isEqualTo("HEADER_VALUE");
    }

    @DisplayName("헤더가 없다.")
    @Test
    void getHeaderNull() {
        // when
        final boolean notExists = request.getHeader("sdfasdfdasf").isEmpty();

        // then
        assertThat(notExists).isTrue();
    }

    @DisplayName("바디를 가져온다.")
    @Test
    void getBody() {
        // when
        final String value = request.getParameter("bodyKey");

        // then
        assertThat(value).isEqualTo("bodyValue");
    }

    @DisplayName("파라미터를 가져온다.")
    @Test
    void getParameter() {
        // when
        final String value = request.getParameter("queryKey");

        // then
        assertThat(value).isEqualTo("queryValue");
    }

    @DisplayName("파라미터가 없으면 null을 반환한다.")
    @Test
    void getParameterNull() {
        // when
        final String value = request.getParameter("sdfsadf");

        // then
        assertThat(value).isEqualTo(null);
    }
}
