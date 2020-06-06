package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("QueryString 테스트")
class QueryStringTest {

    @DisplayName("해당 key를 가진 queryString의 존재 여부를 검증한다.")
    @Test
    void name() {
        QueryString queryString = new QueryString("name", "serverwizard");

        assertAll(
                () -> assertThat(queryString.exists("name")).isTrue(),
                () -> assertThat(queryString.exists("age")).isFalse()
        );
    }
}
