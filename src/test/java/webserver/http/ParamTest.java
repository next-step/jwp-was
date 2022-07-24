package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ParamTest {

    @ParameterizedTest
    @ValueSource(strings = {"userId=javajigi", "password=pass"})
    void create(String param) {
        assertThat(Param.of(param)).isNotNull();
    }
}
