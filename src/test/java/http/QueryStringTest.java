package http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class QueryStringTest {


    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 비어있는_queryString으로_QueryString_객체를_만든다(String input) {
        final QueryString queryString = new QueryString(input);
        assertThat(queryString).isNotNull();
    }

    @Test
    void queryString의_parameterName으로_parameterValue를_정상적으로_가져온다() {
        final String input = "id=30&content=hi";
        final QueryString queryString = new QueryString(input);

        final String idValue = queryString.getParameterValue("id");
        final String contentValue = queryString.getParameterValue("content");

        assertThat(idValue).isEqualTo("30");
        assertThat(contentValue).isEqualTo("hi");
    }

    @Test
    void queryString에_동일한_parameterName이_존재할경우_parameterValue는_뒤에있는_값을_반환한다() {
        final String input = "id=30&id=100";
        final QueryString queryString = new QueryString(input);

        final String idValue = queryString.getParameterValue("id");

        assertThat(idValue).isEqualTo("100");
    }

    @Test
    void 유효하지않은_queryString_값이_올_경우_에러반환한다() {
        final String input = "id30";
        final Throwable thrown = catchThrowable(() ->new QueryString(input));

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("queryString 자르기 실패");
    }


    @Test
    void URL인코딩된_queryString을_넣은_경우_가져올때에는_URL디코딩되어_나온다() {
        final String input = "id=a%20b";
        final QueryString queryString = new QueryString(input);

        final String result = queryString.getParameterValue("id");

        assertThat(result).isEqualTo("a b");
    }
}
