package request;

import exception.NotExistHttpMethodException;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.RequestLine;
import utils.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class RequestLineTest {

    private RequestLine requestLine = RequestLine.getInstance();

    @Test
    @DisplayName("요청 정보에 따른 파싱 (get)")
    void parsing_get() throws IOException {
        BufferedReader br = HelpData.getHelpData("GET");
        RequestLine parsing = requestLine.parsing(br);
        assertThat(parsing.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(parsing.getPath()).isEqualTo("/users");
        assertThat(parsing.getProtocol()).isEqualTo("HTTP");
        assertThat(parsing.getVersion()).isEqualTo("1.1");
    }

    @Test
    @DisplayName("요청정보에 해당하지 않는 http Method가 들어올 경우 예외처리")
    void exception_invalid_http_method() {
        BufferedReader br = HelpData.getHelpData("GETT");
        assertThatThrownBy(() -> requestLine.parsing(br))
                .isInstanceOf(NotExistHttpMethodException.class);
    }

    @Test
    @DisplayName("요청 정보에 따른 파싱 (post)")
    void parsing_post() throws IOException {
        BufferedReader br = HelpData.postHelpData();
        RequestLine parsing = requestLine.parsing(br);
        assertThat(parsing.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(parsing.getPath()).isEqualTo("/users");
        assertThat(parsing.getProtocol()).isEqualTo("HTTP");
        assertThat(parsing.getVersion()).isEqualTo("1.1");
    }


    @Test
    @DisplayName("queryParam 파싱 테스트")
    void query_param() {
        String data = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        assertThat(requestLine.getQueryParam(data)).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }

    @Test
    @DisplayName("queryParam")
    void query_param_get() {
        String requestUri = "/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        User saveUser = requestLine.queryStringToUser(requestUri);
        User user = new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net");
        assertThat(saveUser).isEqualTo(user);
    }
}