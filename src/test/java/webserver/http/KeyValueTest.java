package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class KeyValueTest {

    @ParameterizedTest
    @CsvSource({"userId=javajigi, userId, javajigi", "password=pass, password, pass"})
    void create(String param, String key, String value) {
        final KeyValue keyValue = KeyValue.of(param);

        assertAll(
                () -> assertThat(keyValue.getKey()).isEqualTo(key),
                () -> assertThat(keyValue.getValue()).isEqualTo(value)
        );
    }
}
