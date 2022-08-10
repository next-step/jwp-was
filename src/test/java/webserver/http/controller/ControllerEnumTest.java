package webserver.http.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.controller.ControllerEnum;
import webserver.http.model.request.HttpRequest;
import webserver.http.model.request.RequestBody;
import webserver.http.model.request.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerEnumTest {
    @DisplayName("GET 요청을 통해 회원가입이 성공하면 index.html 로 이동한다.")
    @Test
    void handlerMapping_createUserGet() {
        String requestLineText = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n";

        RequestLine requestLine = new RequestLine(requestLineText);
        HttpRequest httpRequest = new HttpRequest(requestLine, null, null);

        assertThat(ControllerEnum.handlerMapping(httpRequest)).isEqualTo("/index.html");
    }

    @DisplayName("POST 요청을 통해 회원가입이 성공하면 index.html 로 이동한다.")
    @Test
    void handlerMapping_createUserPost() {
        RequestLine requestLine = new RequestLine("POST /user/create HTTP/1.1\n");
        RequestBody requestBody = new RequestBody("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        HttpRequest httpRequest = new HttpRequest(requestLine, null, requestBody);

        assertThat(ControllerEnum.handlerMapping(httpRequest)).isEqualTo("/index.html");
    }
}