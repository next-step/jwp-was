package webserver.http.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserControllerTest {

    private CreateUserController createUserController = new CreateUserController();

    @Test
    void user_생성_테스트() throws IOException {

        String createUserRequest = "POST /user/create HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 35\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=hoon&password=1234&name=test";


        HttpRequest httpRequest = new HttpRequest(new ByteArrayInputStream(createUserRequest.getBytes()));
        HttpResponse httpResponse = new HttpResponse(new ByteArrayOutputStream());

        createUserController.service(httpRequest, httpResponse);

        User user = DataBase.findUserById("hoon");

        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isEqualTo("hoon");
        assertThat(user.getPassword()).isEqualTo("1234");
        assertThat(user.getName()).isEqualTo("test");

    }
}