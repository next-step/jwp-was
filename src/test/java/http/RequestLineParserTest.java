package http;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineParserTest {

    @Test
    void parser() {
        //input 과 output 먼저 검증
        RequestLine requestLine = RequestLineParser.parse2("GET /users HTTP/1.1");

        //그랬을때 결과를 작성하고
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.protocol.getVersion()).isEqualTo("1.1");

        //1. 컴파일 에러부터 해결

        //2. 가장 빠르게 해결

        //3. 리펙토링
        //3.1 클래스로 분리
    }

    @Test
    void parserPost(){
        RequestLine requestLine = RequestLineParser.parse2("POST /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo("POST");
        assertThat(requestLine.getPath()).isEqualTo("/users");
        assertThat(requestLine.protocol.getProtocol()).isEqualTo("HTTP");
        assertThat(requestLine.protocol.getVersion()).isEqualTo("1.1");
    }

}
