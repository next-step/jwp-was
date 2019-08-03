package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParameterTest {
    @Test
    void parameter생성() {
        String input = "userId=testUser";
        Parameter parameter = Parameter.newInstance(input);

        //then
        String[] result = input.split("=");

        assertThat(parameter.getKey()).isEqualTo(result[0]);
        assertThat(parameter.getValue()).isEqualTo(result[1]);
    }
}
