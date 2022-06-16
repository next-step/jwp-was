package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.request.Headers;
import webserver.request.RequestBody;
import webserver.request.RequestLine;
import webserver.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

class UserLoginServiceTest {


    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @Test
    void doPost() {
        //given
        RequestLine requestLine = RequestLine.from("POST /user/login HTTP/1.1");
        DataBase.addUser(new User("changgunyee", "password", "name", "email"));

        //when
        UserLoginService userLoginService = new UserLoginService();
        Response response = userLoginService.doPost(requestLine, RequestBody.from("userId=changgunyee&password=password"));

        //then
        assertThat(response.getCookieStr()).contains("logined=true");
        assertThat(response.getHeaders().get(Headers.LOCATION)).isEqualTo("/index.html");
        assertThat(response.getCode()).isEqualTo("302");
    }

    @Test
    void doPost_FailedLoginException() {
        //given
        RequestLine requestLine = RequestLine.from("POST /user/login HTTP/1.1");

        //when
        UserLoginService userLoginService = new UserLoginService();
        Response response = userLoginService.doPost(requestLine, RequestBody.from("userId=changgunyee&password=password"));

        //then
        assertThat(response.getCookieStr()).contains("logined=false");
        assertThat(response.getHeaders().get(Headers.LOCATION)).isEqualTo("/user/login_failed.html");
        assertThat(response.getCode()).isEqualTo("302");
    }
}