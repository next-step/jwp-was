package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;


class RequestParameterTest {

    @DisplayName("Parse from empty query")
    @ParameterizedTest
    @NullAndEmptySource
    void parseEmptyQuery(String emptyQueryString) {
        RequestParameter requestParameter = RequestParameter.parse(emptyQueryString);

        assertThat(requestParameter).isEqualTo(RequestParameter.EMPTY);
    }

    @DisplayName("Parse from query with or without question mark ")
    @ParameterizedTest
    @ValueSource(strings = {
            "userId=javajigi&password=password&name=JaeSung",
            "?userId=javajigi&password=password&name=JaeSung"
    })
    void parse(String queryString) {
        RequestParameter requestParameter = RequestParameter.parse(queryString);

        assertThat(requestParameter.getParameters())
                .hasSize(3)
                .containsEntry("userId", "javajigi")
                .containsEntry("password", "password")
                .containsEntry("name", "JaeSung");
    }

    @DisplayName("Parse with empty value")
    @ParameterizedTest
    @ValueSource(strings = {
            "userId=",
            "userId"
    })
    void parseEmptyValue(String queryString) {
        RequestParameter requestParameter = RequestParameter.parse(queryString);

        assertThat(requestParameter.getParameters())
                .hasSize(1)
                .containsEntry("userId", "");
    }
}