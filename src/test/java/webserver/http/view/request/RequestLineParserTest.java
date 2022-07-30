package webserver.http.view.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.view.ProtocolParser;
import webserver.http.domain.request.Method;
import webserver.http.domain.Protocol;
import webserver.http.domain.request.RequestLine;
import webserver.http.view.KeyValuePairParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static webserver.http.domain.request.fixture.URIFixture.fixtureWithQueryParameters;

class RequestLineParserTest {
    private RequestLineParser requestLineParser;

    @BeforeEach
    void setUp() {
        URIParser uriParser = new URIParser(
                new KeyValuePairParser(),
                new ParametersParser(new KeyValuePairParser())
        );
        ProtocolParser protocolParser = new ProtocolParser();

        requestLineParser = new RequestLineParser(uriParser, protocolParser);
    }

    @DisplayName("문자열이 white space 값을 기준으로 3개의 문자열로 구분이 되지 않는 경우 예외발생")
    @Test
    void parse_fail() {
        String invalidRequestLineMessage = "GET /uri?name=jordy PROTO/1,1 OTHER-DATA";
        assertThatThrownBy(() -> requestLineParser.parse(invalidRequestLineMessage))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("'[Method] [URI] [Protocol]' 형식의 requestLine 메시지가 아닙니다. {message=" + invalidRequestLineMessage + "}");
    }

    @DisplayName("문자열이 white space 값을 기준으로 3개의 문자열로 구분되는 경우 RequestLine 객체를 생성한다.")
    @Test
    void parse() {
        String requestLineMessage = "GET /uri?name=jordy HTTP/1.1";

        RequestLine actual = requestLineParser.parse(requestLineMessage);

        assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(new RequestLine(
                                Method.GET,
                                fixtureWithQueryParameters("/uri", "name", "jordy"),
                                new Protocol("HTTP", "1.1")
                        ));
    }
}