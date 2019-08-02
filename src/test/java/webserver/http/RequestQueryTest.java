package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class RequestQueryTest {

    @DisplayName("빈값의 요청 쿼리을 생성하면 빈값이다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            " "
    })
    void empty(final String rawRequestQuery) {
        // when
        final RequestQuery requestQuery = RequestQuery.of(rawRequestQuery);

        // then
        assertThat(requestQuery).isEqualTo(RequestQuery.EMPTY);
    }

    @DisplayName("요청 쿼리의 값을 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a", "b", "c"
    })
    void getString(final String key) {
        // given
        final RequestQuery requestQuery = RequestQuery.of("a=a&b=b&c=c");

        // when
        final String value = requestQuery.getString(key);

        // then
        assertThat(value).isEqualTo(key);
    }

    @DisplayName("요청 쿼리의 값이 없으면 null을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a", "b", "c"
    })
    void notFound(final String key) {
        // given
        final RequestQuery requestQuery = RequestQuery.of("a=a&b=b&c=c");
        final String padding = "!!";

        // when
        final String value = requestQuery.getString(key + padding);

        // then
        assertThat(value).isNull();
    }

    @DisplayName("요청 쿼리의 값이 한 개일 때 value가 없어도 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a=",
            "b=",
            "casdsdasda="
    })
    void singleBlank(final String rawRequestQuery) {
        // when
        final RequestQuery requestQuery = RequestQuery.of(rawRequestQuery);

        // then
        assertThat(requestQuery).isNull();
    }

    @DisplayName("요청 쿼리의 값이 여러 개일 때 value가 없어도 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "a=&b=",
            "a=&b=&cds=",
            "a=aaa&b=&cds=sdfgfd",
            "a=aaa&b=&cds="
    })
    void multiBlank(final String rawRequestQuery) {
        // when
        final RequestQuery requestQuery = RequestQuery.of(rawRequestQuery);

        // then
        assertThat(requestQuery).isNull();
    }
}
