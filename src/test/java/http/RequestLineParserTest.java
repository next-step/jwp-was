package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RequestLineParserTest {

    @Test
    void parser() {
        //input 과 output 먼저 검증
        RequestLine requestLine = RequestLineParser.parse("GET /users HTTP/1.1");

        //그랬을때 결과를 작성하고
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");

        //1. 컴파일 에러부터 해결

        //2. 가장 빠르게 해결

        //3. 리펙토링
        //3.1 클래스로 분리
    }

    @Test
    void parserPost(){
        RequestLine requestLine = RequestLineParser.parse("POST /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void parserGetWithQueryString() {
        RequestLine requestLine = RequestLineParser.parse("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.getVersion()).isEqualTo("1.1");
        assertThat(requestLine.getParameter("userId")).isEqualTo("javajigi");
        assertThat(requestLine.getParameter("password")).isEqualTo("password");
        assertThat(requestLine.getParameter("name")).isEqualTo("JaeSung");
    }

    @Test
    void validEnum() {
        assertThatThrownBy(() -> {
            RequestLine requestLine = RequestLineParser.parse("PUT /users HTTP/1.1");
        }).hasMessageContaining("No enum constant");
    }
}

