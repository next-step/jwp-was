package service;

import db.DataBase;
import org.junit.jupiter.api.Test;
import utils.IOUtils;
import webserver.request.Headers;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateServiceTest {

    @Test
    void doGet() throws IOException {
        //given
        String requestStr = "GET /users?userId=javajigi&password=password&name=JaeSung&email=abc@naver.com HTTP/1.1";
        HttpRequest httpRequest = HttpRequest.from(IOUtils.toBufferedReader(requestStr));

        //when
        UserCreateController userCreateService = new UserCreateController();
        HttpResponse httpResponse = userCreateService.doGet(httpRequest);

        //then
        assertThat(DataBase.findUserById("javajigi")).isNotNull();
        assertThat(httpResponse.getCode()).isEqualTo("302");
        assertThat(httpResponse.getHeaders().get(Headers.LOCATION)).isEqualTo("/index.html");
    }
}