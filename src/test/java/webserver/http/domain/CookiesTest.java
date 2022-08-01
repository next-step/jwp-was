package webserver.http.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesTest {

    @DisplayName("key1=value1; key2=value2; ... 형식으로 이뤄진 문자열을 Cookies 객체로 파싱한다.")
    @Test
    void from() {
        String message = "Idea=7d39d793-8ebb-45b8-9e1e-7c66b1449982; logined=true; other=";
        Cookies actual = Cookies.from(message);

        assertThat(actual).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(new Cookies(
                        Map.of(
                                "Idea", new Cookie("Idea", "7d39d793-8ebb-45b8-9e1e-7c66b1449982"),
                                "logined", new Cookie("logined", "true"),
                                "other", new Cookie("other", "")
                        )
                ));
    }

    @DisplayName("nam, valuee가 일치하는 쿠키의 존재여부를 반환")
    @ParameterizedTest
    @CsvSource(value = {"logined, true, true", "invalid, value, false", "logined, false, false"})
    void existsCookie(String name, String value, boolean expected) {
        Cookies cookies = new Cookies(
                Map.of(
                        "Idea", new Cookie("Idea", "7d39d793-8ebb-45b8-9e1e-7c66b1449982"),
                        "logined", new Cookie("logined", "true"),
                        "other", new Cookie("other", "")
                )
        );

        boolean actual = cookies.existsCookie(name, value);
        assertThat(actual).isEqualTo(expected);
    }
}