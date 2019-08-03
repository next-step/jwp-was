package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParameterTest {
    @Test
    void parameter생성() {
        String key = "userId";
        String value = "testUser";
        Parameter parameter = Parameter.newInstance(key, value);

        assertThat(parameter.getKey()).isEqualTo(key);
        assertThat(parameter.getValue()).isEqualTo(value);
    }
}
