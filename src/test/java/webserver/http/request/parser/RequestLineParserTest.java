package webserver.http.request.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import webserver.http.request.Method;
import webserver.http.request.Protocol;
import webserver.http.request.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static webserver.http.request.parser.fixture.URIFixture.fixtureWithQueryParameters;

@ExtendWith(MockitoExtension.class)
class RequestLineParserTest {

    @InjectMocks
    private RequestLineParser requestLineParser;

    @Mock
    private MethodParser methodParser;
    @Mock
    private URIParser uriParser;
    @Mock
    private ProtocolParser protocolParser;

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
        when(methodParser.parse(any())).thenReturn(Method.GET);
        when(uriParser.parse(any())).thenReturn(fixtureWithQueryParameters("/uri", "name", "jordy"));
        when(protocolParser.parse(any())).thenReturn(new Protocol("HTTP", "1.1"));

        RequestLine actual = requestLineParser.parse(requestLineMessage);

        assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(new RequestLine(
                                Method.GET,
                                fixtureWithQueryParameters("/uri", "name", "jordy"),
                                new Protocol("HTTP", "1.1")
                        ));
        verify(methodParser).parse("GET");
        verify(uriParser).parse("/uri?name=jordy");
        verify(protocolParser).parse("HTTP/1.1");
    }
}