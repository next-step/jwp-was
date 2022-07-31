package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("CastingUtils 단위 테스트")
class CastingUtilsTest {
    @DisplayName("String 타입의 Object를 String으로 형변환한다.")
    @Test
    void CastString() {
        // when
        final String result = CastingUtils.cast("test", String.class);

        // then
        assertThat(result).isEqualTo("test");
    }

    @DisplayName("Boolean 타입의 Object를 Boolean으로 형변환한다.")
    @Test
    void CastBoolean1() {
        // when
        final Boolean result = CastingUtils.cast(true, Boolean.class);

        // then
        assertThat(result).isEqualTo(true);
    }

    @DisplayName("String 타입의 Object를 Boolean으로 형변환한다.")
    @Test
    void CastBoolean2() {
        // when
        final Boolean result = CastingUtils.cast("true", Boolean.class);

        // then
        assertThat(result).isEqualTo(true);
    }
}
