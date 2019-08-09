package webserver.http.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.cookie.Cookies;
import webserver.http.session.MockSessionStore;

import static org.assertj.core.api.Assertions.assertThat;
import static support.FileSupporter.read;

class GetRequestTest {

    private Request request;

    @BeforeEach
    void setUp() throws Exception {
        request = HttpRequest.of(read("GET_Request.txt"), new MockSessionStore());
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
    void getCookieEmpty() {
        // when
        final Cookies cookies = request.getCookies();
        final boolean notExists = cookies.getString("sdfadadsf").isEmpty();

        // then
        assertThat(notExists).isTrue();
    }

    @DisplayName("경로 매칭을 확인한다.")
    @Test
    void matchPath() {
        // when
        final boolean isMatch = request.matchPath("/get-test");

        // then
        assertThat(isMatch).isTrue();
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
    void getHeaderEmpty() {
        // when
        final boolean notExists = request.getHeader("sdfasdfdasf").isEmpty();

        // then
        assertThat(notExists).isTrue();
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
