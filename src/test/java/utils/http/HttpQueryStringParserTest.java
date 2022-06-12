package utils.http;

import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpQueryStringParserTest {

    @Test
    void parse() {
        // given
        String queryString = "userId=javajigi&password=password&name=JaeSung";

        // when
        Map<String, String> queryStringMap = HttpQueryStringParser.parse(queryString);

        // then
        assertThat(queryStringMap).isNotEmpty();
        assertThat(queryStringMap).containsEntry("userId", "javajigi");
        assertThat(queryStringMap).containsEntry("password", "password");
        assertThat(queryStringMap).containsEntry("name", "JaeSung");
    }
}
