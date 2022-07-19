package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestPathTest {

    @DisplayName("RequestPath 는 '/' 으로 시작해야 한다.")
    @Test
    void validateRequestPathTest() {
        assertThatThrownBy(() -> new RequestPath("user"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[user] RequestPath 형식이 아닙니다.");
    }

    @DisplayName("RequestPath 를 파싱해서 쿼리스트링을 알 수 있다.")
    @CsvSource(
            value = {
                    "/users:''",
                    "/users?userId=javajigi:userId=javajigi",
                    "/users?userId=javajigi&password=password:userId=javajigi&password=password",
            },
            delimiter = ':')
    @ParameterizedTest
    void parseQueryStringTest(String path, String queryString) {
        // given
        RequestPath requestPath = new RequestPath(path);

        // when
        String actualQueryString = requestPath.getQueryString();

        // then
        assertThat(actualQueryString).isEqualTo(queryString);
    }

}