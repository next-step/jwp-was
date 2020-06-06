package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("스트링 토큰 관리 클래스")
public class TokensTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("문자열 혹은 구분자가 null 혹은 빈 문자열인 경우에는 예외를 던진다")
    void tokenizeFailWithNull(final String origin, final String delimiter) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Tokens(origin, delimiter));
    }

    private static Stream<Arguments> tokenizeFailWithNull() {
        return Stream.of(
                Arguments.of(null, ","),
                Arguments.of(null, ""),
                Arguments.of("origin", null),
                Arguments.of("origin", ""),
                Arguments.of("", ""),
                Arguments.of(null, null)
        );
    }


    @Test
    @DisplayName("문자열은 구분자로 토큰화")
    void tokenize() {
        Tokens tokens = new Tokens("origin,string", ",");

        assertThat(tokens.nextToken()).isEqualTo("origin");
        assertThat(tokens.nextToken()).isEqualTo("string");
    }

    @Test
    @DisplayName("문자열을 토큰화 했들때 토큰 갯수를 검증한다.")
    void validate() {
        Tokens tokens = new Tokens("a,b,c", ",");

        assertThatCode(() -> tokens.validate(3)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("문자열을 토큰화 했들때 토큰 갯수가 예상보다 적을때 예외 생성")
    void validateThrowException() {
        Tokens tokens = new Tokens("a,b,c", ",");

        assertThatIllegalArgumentException()
                .isThrownBy(() -> tokens.validate(4));
    }

    @Test
    @DisplayName("토큰을 한번에 가져오기")
    void getAllTokens() {
        Tokens tokens = new Tokens("a,b,c", ",");

        assertThat(tokens.getAllTokens()).containsExactly("a", "b", "c");
    }
}
