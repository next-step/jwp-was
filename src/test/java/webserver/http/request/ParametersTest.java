package webserver.http.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ParametersTest {
    @Test
    void 쿼리_스트링_파싱() {
        String key1 = "id";
        String key2 = "password";
        String key3 = "name";
        String value1 = "skid901";
        String value2 = "pw1234";

        Parameters parameters = Parameters.from(key1 + "=" + value1 + "&" + key2 + "=" + value2);

        assertAll(
                () -> assertThat(parameters.get(key1)).isEqualTo(value1),
                () -> assertThat(parameters.get(key2)).isEqualTo(value2),
                () -> assertThat(parameters.get(key3)).isNull()
        );
    }

    @ParameterizedTest
    @EmptySource
    void 쿼리_스트링이_없을_경우(String queryString) {
        Parameters parameters = Parameters.from(queryString);

        assertThat(parameters.get("key")).isNull();
    }
}
