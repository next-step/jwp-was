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
        final String value = cookies.getString("hello");

        // then
        assertThat(value).isEqualTo("world");
    }

    @DisplayName("쿠키가 없으면 null을 반환한다.")
    @Test
    void getCookiesNull() {
        // when
        final Cookies cookies = request.getCookies();
        final String value = cookies.getString("sdfadadsf");

        // then
        assertThat(value).isEqualTo(null);
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
        final String value = request.getHeader("HEADER_KEY");

        // then
        assertThat(value).isEqualTo("HEADER_VALUE");
    }

    @DisplayName("헤더가 없으면 null을 반환한다.")
    @Test
    void getHeaderNull() {
        // when
        final String value = request.getHeader("sdfasdfdasf");

        // then
        assertThat(value).isEqualTo(null);
    }

    @DisplayName("파라미터를 가져온다.")
    @Test
    void getParameter() {
        // when
        final String value = request.getParameter("bodyKey");

        // then
        assertThat(value).isEqualTo("bodyValue");
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
