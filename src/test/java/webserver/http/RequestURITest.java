package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestURITest {

    @DisplayName("RequestPath 는 '/' 으로 시작해야 한다.")
    @Test
    void validateRequestPathTest() {
        assertThatThrownBy(() -> new RequestURI("user"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효한 RequestPath 가 아님");
    }

    @DisplayName("RequestPath 를 파싱해서 쿼리스트링의 key 에 대한 value 를 알 수 있다.")
    @Test
    void parseQueryStringTest() {
        // given
        RequestURI requestUri = new RequestURI("/users?userId=javajigi&password=password");

        // when
        QueryString queryString = requestUri.parseQueryString();

        // then
        assertThat(queryString.getValue("userId")).isEqualTo("javajigi");
        assertThat(queryString.getValue("password")).isEqualTo("password");
        assertThat(queryString.getValue("password2")).isNull();
    }

}
