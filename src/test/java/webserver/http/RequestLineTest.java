package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    /**
     * 테스트를 먼저 만든뒤 코딩을 시작한다
     * 컴파일 에러난 상태에서 테스트 코드를 만든다
     *
     */
    @Test
    @DisplayName("URL 파싱")
    void parse(){
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getPath()).isEqualTo("/users");
    }

    @DisplayName("Step1-1 파라미터 파싱")
    @ParameterizedTest
    @CsvSource({"GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1, password",
            "GET /users?userId=javajigi&password=&name=JaeSung HTTP/1.1, "
    })
    void parseParameter(String url, String value){
        RequestLine requestLine = RequestLine.parse(url);
        assertThat(requestLine.getParam("userId")).isEqualTo("javajigi");
        assertThat(requestLine.getParam("password")).isEqualTo(value);
        assertThat(requestLine.getParam("name")).isEqualTo("JaeSung");
    }

}
