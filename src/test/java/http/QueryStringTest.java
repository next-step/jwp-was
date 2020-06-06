package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class QueryStringTest {
    @DisplayName("쿼리스트링을 변수와 값의 쌍 형태로 파싱")
    @Test
    void test_parsing_querystring_should_pass() {
        // given
        String fullQueryString = "userId=javajigi&password=password&name=JaeSung";
        // when
        QueryString queryString = QueryString.from(fullQueryString);
        // then
        assertThat(queryString.getParameters("userId")).hasSize(1).containsExactly("javajigi");
        assertThat(queryString.getParameters("password")).hasSize(1).containsExactly("password");
        assertThat(queryString.getParameters("name")).hasSize(1).containsExactly("JaeSung");
    }
}
