package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringUtilsTest {

    @DisplayName("빈 문자열이면 null로 변환 ")
    @Test
    void test_converToNullIfEmpty() {
        // given
        String text = "";
        // when
        String result = StringUtils.convertToNullIfEmpty(text);
        // then
        assertThat(result).isNull();
    }

    @DisplayName("구분자가 1개이고 키와 값 형태로  분할")
    @Test
    void test_splitIntoPair() {
        // given
        String text = "userId=a";
        // when
        String[] result = StringUtils.splitIntoPair(text, "=");
        // then
        assertThat(result).containsExactly("userId", "a");
    }

    @DisplayName("URI 퍼센트 디코딩 시 null 값은 그대로 반환")
    @Test
    void test_percent_decoding_null() {
        // given
        // when
        String result = StringUtils.decode(null);
        // then
        assertThat(result).isNull();
    }
}