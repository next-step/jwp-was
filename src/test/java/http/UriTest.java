package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class UriTest {

    @DisplayName("요청 URI 경로와 쿼리스트링으로 파싱")
    @Test
    void test_parsing_uri_should_pass() {
        // given
        String fullUri = "/users?userId=javajigi&password=password&name=JaeSung";
        // when
        Uri uri = Uri.from(fullUri);
        // then
        assertThat(uri.equals(Uri.of("/users", "userId=javajigi&password=password&name=JaeSung"))).isTrue();
    }

    @DisplayName("쿼리스트링 없이 경로만 존재하면 쿼리스트링은 비어있다.")
    @Test
    void parse_uri_without_querystring() {
        // given
        String fullUri = "/users";
        String noQueryString = "";
        // when
        Uri uri = Uri.from(fullUri);
        // then
        assertThat(uri.equals(Uri.of(fullUri, noQueryString))).isTrue();
    }
}
