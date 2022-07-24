package webserver.http.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class QueriesTest {
    @Test
    void 쿼리_스트링_파싱() {
        String key1 = "id";
        String key2 = "password";
        String key3 = "name";
        String value1 = "skid901";
        String value2 = "pw1234";

        Queries queries = Queries.from(key1 + "=" + value1 + "&" + key2 + "=" + value2);
        Optional<String> queryValue1 = queries.get(key1);
        Optional<String> queryValue2 = queries.get(key2);
        Optional<String> queryValue3 = queries.get(key3);

        assertAll(
                () -> assertThat(queryValue1.isPresent()).isTrue(),
                () -> assertThat(queryValue2.isPresent()).isTrue(),
                () -> assertThat(queryValue3.isPresent()).isFalse(),
                () -> assertThat(queryValue1.get()).isEqualTo(value1),
                () -> assertThat(queryValue2.get()).isEqualTo(value2)
        );
    }

    @ParameterizedTest
    @EmptySource
    void 쿼리_스트링이_없을_경우(String queryString) {
        Queries queries = Queries.from(queryString);

        Optional<String> queryValue = queries.get("key");

        assertThat(queryValue.isPresent()).isFalse();
    }
}
