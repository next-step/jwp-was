package webserver.http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UrlEncodedBodyParserTest {

    @DisplayName("content-type 이 application/x-www-form-urlencoded 인 request body 를 파싱할 수 있다.")
    @Test
    void parseTest() {
        // given
        UrlEncodedBodyParser parser = new UrlEncodedBodyParser();

        // when
        Map<String, String> parseBody = parser.parseBody(new RequestBody("userId=javajigi&password=password&name=재성&email=javajigi@slipp.net"));

        // then
        assertThat(parseBody.get("userId")).isEqualTo("javajigi");
        assertThat(parseBody.get("password")).isEqualTo("password");
        assertThat(parseBody.get("name")).isEqualTo("재성");
        assertThat(parseBody.get("email")).isEqualTo("javajigi@slipp.net");

    }

}