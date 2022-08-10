package study;

import model.HttpMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumTest {

    @Test
    void enum_valueOf_테스트() {
        assertThat(HttpMethod.valueOf("GET")).isEqualTo(HttpMethod.GET);
    }
}
