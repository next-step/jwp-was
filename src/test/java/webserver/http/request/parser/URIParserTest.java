package webserver.http.request.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.QueryParameters;
import webserver.http.request.URI;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.request.parser.fixture.URIFixture.fixtureWithQueryParameters;

class URIParserTest {
    private final URIParser uriParser = new URIParser(new KeyValuePairParser(), new QueryParametersParser(new KeyValuePairParser()));

    @DisplayName("queryParameter가 포함되지 않는 메시지인 경우, path와 빈 QueryParameters를 갖는 URI 객체를 생성한다.")
    @Test
    void parse_empty_query_parameter() {
        String message = "/hello";
        URI actual = uriParser.parse(message);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new URI("/hello", new QueryParameters(new HashMap<>())));
    }

    @DisplayName("queryParameter가 포함된 메시지인 경우, path와 빈 QueryParameters를 갖는 URI 객체를 생성한다.")
    @Test
    void parse() {
        String message = "/hello?name=jordy";
        URI actual = uriParser.parse(message);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(fixtureWithQueryParameters("/hello", "name", "jordy"));
    }
}