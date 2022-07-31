package webserver.http.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.Cookie;
import webserver.http.domain.Cookies;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CookiesParserTest {

    @DisplayName("key1=value1; key2=value2; ... 형식으로 이뤄진 문자열을 Cookies 객체로 파싱한다.")
    @Test
    void parse() {
        CookiesParser cookiesParser = new CookiesParser(new KeyValuePairParser());
        String message = "Idea=7d39d793-8ebb-45b8-9e1e-7c66b1449982; logined=true; other=";
        Cookies actual = cookiesParser.parse(message);

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
}