package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("QueryString 테스트")
class QueryStringTest {

    @DisplayName("해당 key를 가진 queryString이 존재하는지 확인한다.")
    @Test
    void name() {
        QueryString queryString = new QueryString("name", "serverwizard");
        assertThat(queryString.exists("name")).isTrue();
    }
}
