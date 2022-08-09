package webserver.http.model;

import exception.IllegalHttpRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QueryStringTest {

    @DisplayName("Query String이 키, 값 한쌍으로 이루어 지지 않은 경우")
    @ParameterizedTest
    @ValueSource(strings = {"userId", "userId=youngsu=1"})
    void construct_notStartWithQuestionMark(String queryString) {
        assertThatThrownBy(() -> new QueryString(queryString))
                .isInstanceOf(IllegalHttpRequestException.class)
                .hasMessageContaining("Query String은 키, 값 한쌍으로 이루어져 있습니다.");
    }

    @DisplayName("정상적으로 Query String 값 생성되고 입력")
    @Test
    void construct() {
        QueryString queryString = new QueryString("userId=javajigi");
        assertThat(queryString.getQueryStringKey()).isEqualTo(new QueryStringKey("userId"));
        assertThat(queryString.getQueryStringValue()).isEqualTo(new QueryStringValue("javajigi"));
    }
}