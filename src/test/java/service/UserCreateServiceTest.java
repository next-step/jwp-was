package service;

import db.DataBase;
import org.junit.jupiter.api.Test;
import webserver.request.Headers;
import webserver.request.RequestLine;
import webserver.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateServiceTest {

    @Test
    void doGet() {
        //given
        RequestLine requestLine = RequestLine.from("GET /users?userId=javajigi&password=password&name=JaeSung&email=abc@naver.com HTTP/1.1");

        //when
        UserCreateService userCreateService = new UserCreateService();
        Response response = userCreateService.doGet(requestLine);

        //then
        assertThat(DataBase.findUserById("javajigi")).isNotNull();
        assertThat(response.getCode()).isEqualTo("302");
        assertThat(response.getHeaders().get(Headers.LOCATION)).isEqualTo("/index.html");
    }
}