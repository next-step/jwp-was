package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.IOUtils;
import webserver.request.Headers;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UserLoginControllerTest {


    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @Test
    void doPost() throws IOException {
        //given
        String requestStr = "POST /user/create HTTP/1.1\n" +
                "Content-Length: 36\n" +
                "\n" +
                "userId=changgunyee&password=password";

        HttpRequest httpRequest = HttpRequest.from(IOUtils.toBufferedReader(requestStr));
        DataBase.addUser(new User("changgunyee", "password", "name", "email"));

        //when
        UserLoginController userLoginService = new UserLoginController();
        HttpResponse httpResponse = userLoginService.doPost(httpRequest);

        //then
        assertThat(httpResponse.getCookieStr()).contains("logined=true");
        assertThat(httpResponse.getHeaders().get(Headers.LOCATION)).isEqualTo("/index.html");
        assertThat(httpResponse.getCode()).isEqualTo("302");
    }

    @Test
    void doPost_FailedLoginException() throws IOException {
        //given
        String requestStr = "POST /user/login HTTP/1.1";
        HttpRequest httpRequest = HttpRequest.from(IOUtils.toBufferedReader(requestStr));


        //when
        UserLoginController userLoginService = new UserLoginController();
        HttpResponse httpResponse = userLoginService.doPost(httpRequest);

        //then
        assertThat(httpResponse.getCookieStr()).contains("logined=false");
        assertThat(httpResponse.getHeaders().get(Headers.LOCATION)).isEqualTo("/user/login_failed.html");
        assertThat(httpResponse.getCode()).isEqualTo("302");
    }
}