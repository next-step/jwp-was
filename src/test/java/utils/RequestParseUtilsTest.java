package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestParseUtilsTest {

    @DisplayName("빈 문자열이면 null로 변환 ")
    @Test
    void test_converToNullIfEmpty() {
        // given
        String text = "";
        // when
        String result = RequestParseUtils.convertToNullIfEmpty(text);
        // then
        assertThat(result).isNull();
    }

    @DisplayName("구분자가 1개이고 키와 값 형태로  분할")
    @Test
    void test_splitIntoPair() {
        // given
        String text = "userId=a";
        // when
        String[] result = RequestParseUtils.splitIntoPair(text, "=");
        // then
        assertThat(result).containsExactly("userId", "a");
    }
}