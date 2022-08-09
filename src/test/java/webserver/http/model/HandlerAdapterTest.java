package webserver.http.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerAdapterTest {
    @DisplayName("요청 경로를 넣어 해당 경로에 해당하는 메서드를 실행한다.")
    @Test
    void handlerMapping_createUserGet() {
        String requestLineText = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n";

        RequestLine requestLine = new RequestLine(requestLineText);
        HttpRequest httpRequest = new HttpRequest(requestLine, null, null);

        assertThat(HandlerAdapter.handlerMapping(httpRequest)).isEqualTo("/index.html");
    }

    @DisplayName("요청 경로를 넣어 해당 경로에 해당하는 메서드를 실행한다.")
    @Test
    void handlerMapping_createUserPost() {
        RequestLine requestLine = new RequestLine("POST /user/create HTTP/1.1\n");
        RequestBody requestBody = new RequestBody("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        HttpRequest httpRequest = new HttpRequest(requestLine, null, requestBody);

        assertThat(HandlerAdapter.handlerMapping(httpRequest)).isEqualTo("/index.html");
    }
}