package webserver;

import org.junit.jupiter.api.Test;
import webserver.request.QueryParameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class QueryParametersTest {

    @Test
    void QueryString을_Map으로_변환한다() {
        // when
        final QueryParameters queryParameters = new QueryParameters("userId=javajigi&password=JaeSung");

        // then
        assertThat(queryParameters.getParameters())
                .hasSize(2)
                .contains(
                        entry("userId", "javajigi"),
                        entry("password", "JaeSung")
                );
    }

    @Test
    void 특정_파라미터를_가져온다() {
        final QueryParameters queryParameters = new QueryParameters("userId=javajigi&password=JaeSung");
        assertThat(queryParameters.getParameterOrNull("userId")).isEqualTo("javajigi");
        assertThat(queryParameters.getParameterOrNull("password")).isEqualTo("JaeSung");
    }

    @Test
    void 존재하지_않는_파라미터는_null을_리턴한다() {
        final QueryParameters queryParameters = new QueryParameters("userId=javajigi&password=JaeSung");
        assertThat(queryParameters.getParameterOrNull("notExisting")).isNull();
    }

}
