package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.exceptions.ErrorMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class QueryStringTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("비어있는 queryString으로 QueryString 객체를 만든다")
    void test1(String input) {
        final QueryString queryString = new QueryString(input);
        assertThat(queryString).isNotNull();
    }

    @Test
    @DisplayName("queryString의 parameterName으로 parameterValue를 정상적으로 가져온다")
    void test2() {
        final String input = "id=30&content=hi";
        final QueryString queryString = new QueryString(input);

        final String idValue = queryString.getParameterValue("id");
        final String contentValue = queryString.getParameterValue("content");

        assertThat(idValue).isEqualTo("30");
        assertThat(contentValue).isEqualTo("hi");
    }

    @Test
    @DisplayName("queryString에 동일한 parameterName이 존재할 경우 parameterValue는 뒤에있는 값을 반환한다")
    void test3() {
        final String input = "id=30&id=100";
        final QueryString queryString = new QueryString(input);

        final String idValue = queryString.getParameterValue("id");

        assertThat(idValue).isEqualTo("100");
    }

    @Test
    @DisplayName("유효하지 않은 queryString 값이 올 경우 에러를 반환한다")
    void test4() {
        final String input = "id30";
        final Throwable thrown = catchThrowable(() ->new QueryString(input));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(ErrorMessage.ILLEGAL_QUERY_STRING.getMessage());
    }


    @Test
    @DisplayName("URL인코딩된 queryString을 넣은 경우 가져올 때에는 URL디코딩되어 나온다")
    void test5() {
        final String input = "id=a%20b";
        final QueryString queryString = new QueryString(input);

        final String result = queryString.getParameterValue("id");

        assertThat(result).isEqualTo("a b");
    }
}
