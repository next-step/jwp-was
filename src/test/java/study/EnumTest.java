package study;

import model.HttpMethodType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumTest {

    @Test
    void enum_valueOf_테스트() {
        assertThat(HttpMethodType.valueOf("GET")).isEqualTo(HttpMethodType.GET);
    }
}
