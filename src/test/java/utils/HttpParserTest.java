package utils;

import exception.HttpNotFoundException;
import exception.ProtocolNotFoundException;
import model.RequestLine;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

public class HttpParserTest {

    @DisplayName("RequestLine method, protocol, version 파싱 검증")
    @ParameterizedTest
    @ValueSource(strings = {"GET /users?userId=fistkim101&password=1004 HTTP/1.1", "POST /users?userId=fistkim101&password=1004 HTTP/1.1"})
    void parseRequestLineTest(String httpRequestFirstLine) {

        // given
        String[] requestLineData = httpRequestFirstLine.split(" ");
        String httpMethod = requestLineData[0];
        String protocol = requestLineData[2].split("/")[0];
        String protocolVersion = requestLineData[2].split("/")[1];

        // when
        RequestLine requestLine = HttpParser.parseRequestLine(httpRequestFirstLine);

        // then
        Assertions.assertThat(httpMethod).isEqualTo(requestLine.getHttpMethod().toString());
        Assertions.assertThat(protocol).isEqualTo(requestLine.getProtocol().toString());
        Assertions.assertThat(protocolVersion).isEqualTo(requestLine.getProtocolVersion());

    }

    @DisplayName("RequestLine path, queryParameters 파싱 검증")
    @ParameterizedTest
    @ValueSource(strings = {"GET /users?userId=fistkim101&password=1004 HTTP/1.1"})
    void parseRequestLineQueryParametersTest(String httpRequestFirstLine) {

        // given
        String path = "/users";
        Map<String, String> queryParameters = new HashMap<>(Map.of("userId", "fistkim101", "password", "1004"));

        // when
        RequestLine requestLine = HttpParser.parseRequestLine(httpRequestFirstLine);
        String parsedPath = requestLine.getPath();
        Map<String, String> parsedQueryParameters = requestLine.getQueryParameters();

        // then
        Assertions.assertThat(path).isEqualTo(parsedPath);
        Assertions.assertThat(queryParameters.keySet().size()).isEqualTo(parsedQueryParameters.keySet().size());
        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            Assertions.assertThat(value).isEqualTo(parsedQueryParameters.get(key));
        }

    }

    @DisplayName("RequestLine method 파싱시 에러케이스 검증")
    @ParameterizedTest
    @ValueSource(strings = {"GOT /users?userId=fistkim101&password=1004 HTTP/1.1", "PST /users?name=jk&phoneNumber=01012345678 HTTP/1.1"})
    void parseRequestLineHttpMethodExceptionTest(String httpRequestFirstLine) {

        Assertions.assertThatExceptionOfType(HttpNotFoundException.class)
                .isThrownBy(() -> HttpParser.parseRequestLine(httpRequestFirstLine));

    }

    @DisplayName("RequestLine protocol 파싱시 에러케이스 검증")
    @ParameterizedTest
    @ValueSource(strings = {"GET /users?userId=fistkim101&password=1004 HTTTTP/1.1", "POST /users?name=jk&phoneNumber=01012345678 TP/1.1"})
    void parseRequestLineProtocolExceptionTest(String httpRequestFirstLine) {

        Assertions.assertThatExceptionOfType(ProtocolNotFoundException.class)
                .isThrownBy(() -> HttpParser.parseRequestLine(httpRequestFirstLine));

    }

}
