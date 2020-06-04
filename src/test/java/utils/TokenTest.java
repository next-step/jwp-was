package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("스트링 토큰 관리 클래스")
public class TokenTest {

    @Test
    @DisplayName("문자열은 구분자로 토큰화")
    void tokenize() {
        Token token = new Token("origin,string", ",");

        assertThat(token.nextToken()).isEqualTo("origin");
        assertThat(token.nextToken()).isEqualTo("string");
    }
}
