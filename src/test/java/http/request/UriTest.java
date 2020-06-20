package http;

import http.request.QueryString;
import http.request.Uri;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class UriTest {

    @DisplayName("요청 URI 경로와 쿼리스트링으로 파싱")
    @Test
    void test_parsing_uri_should_pass() {
        // given
        String fullUri = "/users?userId=crystal&password=password&name=Sujung";
        // when
        Uri uri = Uri.from(fullUri);
        // then
        assertThat(uri.getPath()).isEqualTo("/users");
        assertThat(uri.getQueryString()).isEqualTo(new QueryString("userId=crystal&password=password&name=Sujung"));
//        assertThat(uri.equals(Uri.of("/users", "userId=crystal&password=password&name=Sujung"))).isTrue();
    }

    @DisplayName("쿼리스트링 없이 경로만 존재하면 쿼리스트링은 비어있다.")
    @Test
    void parse_uri_without_querystring() {
        // given
        String fullUri = "/users";
        // when
        Uri uri = Uri.from(fullUri);
        // then
        assertThat(uri.getPath()).isEqualTo("/users");
        assertThat(uri.getQueryString()).isEqualTo(new QueryString(""));
    }

    @DisplayName("url이 정적자원을 지칭하는 경우 확장자 구하기")
    @Test
    void test_getExtension() {
        // given
        String fullUri = "/user/index.html";
        // when
        Uri uri = Uri.from(fullUri);
        // then
        assertThat(uri.getExtension()).isEqualTo("html");
    }
}
