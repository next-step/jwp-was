package webserver.http.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import webserver.http.request.header.RequestHeader;
import webserver.http.service.CreateBody;

class CreateBodyTest {

    @DisplayName("GET /user/list 로그인 한 경우")
    @Test
    void userList() throws IOException {
        // given
        DataBase.addUser(new User("test01", "password01", "테스트01", "test01@email.com"));

        String request = "GET /user/list HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Cookie: logined=true";

        CreateBody body = new CreateBody();

        // when
        byte[] handle = body.create(RequestHeader.create(request));

        // then
        assertThat(handle.length).isNotZero();
    }
}