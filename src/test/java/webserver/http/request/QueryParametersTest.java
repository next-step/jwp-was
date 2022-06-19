package webserver.http.request;

import org.junit.jupiter.api.Test;
import webserver.http.request.QueryParameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class QueryParametersTest {
    @Test
    void getParameters는_모든_파라미터를_리턴한다() {
        // given
        final QueryParameters queryParameters = new QueryParameters("a=1&b=2");

        // when
        assertThat(queryParameters.getParameters())
                // then
                .containsExactly(
                        entry("a", "1"),
                        entry("b", "2")
                );
    }

    @Test
    void getParameterOrNull은_갖고있는_파라미터를_가져온다() {
        // given
        final QueryParameters queryParameters = new QueryParameters("a=1&b=2");

        // when
        assertThat(queryParameters.getOrNull("a")).isEqualTo("1");
        assertThat(queryParameters.getOrNull("b")).isEqualTo("2");
    }

    @Test
    void getParameterOrNull은_갖고있지않은_파라미터에_대해_null을_리턴한다() {
        // given
        final QueryParameters queryParameters = new QueryParameters("a=1&b=2");

        // when
        assertThat(queryParameters.getOrNull("c")).isNull();
    }
}
