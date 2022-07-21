package webserver.http.request.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.URI;

import static org.assertj.core.api.Assertions.assertThat;

class URIParserTest {
    private final URIParser uriParser = new URIParser();

    @DisplayName("입력받은 메시지값을 path로 갖는 URI 객체를 생성한다.")
    @Test
    void parse() {
        String message = "/hello?name=jordy";
        URI actual = uriParser.parse(message);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new URI("/hello?name=jordy"));
    }
}