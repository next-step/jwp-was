package webserver.http.request.header;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.header.exception.InvalidPathException;
import webserver.http.request.header.Path;

class PathTest {

    @DisplayName("QueryString은 빈 문자열 일 수 없다.")
    @Test
    void createFailedByEmptyString() {
        // given
        // when
        // then
        assertThatThrownBy(() -> Path.create(""))
                .isInstanceOf(InvalidPathException.class)
                .hasMessage("유효하지 않은 path 입니다");
    }

    @DisplayName("query param이 없는 경우 생성 성공")
    @Test
    void create() {
        // given
        String path = "/user";

        // when
        Path queryString = Path.create(path);

        // then
        assertThat(queryString.index()).isEqualTo(path);
        assertThat(queryString.requestParams()).isEmpty();
    }

    @DisplayName("query param이 존재하는 경우 생성 성공")
    @Test
    void createWithQueryParams() {
        // given
        String path = "/user?name=test&age=20";

        // when
        Path queryString = Path.create(path);

        // then
        assertThat(queryString.index()).isEqualTo("/user");
        assertThat(queryString.requestParams()).hasSize(2);
        assertThat(queryString.requestParams()).containsKey("name").containsValue("test");
        assertThat(queryString.requestParams()).containsKey("age").containsValue("20");
    }
}
