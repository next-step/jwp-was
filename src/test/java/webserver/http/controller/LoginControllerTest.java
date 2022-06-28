package webserver.http.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestLine;
import webserver.http.response.HttpResponse;

import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    @DisplayName("로그인 성공시 index 페이지로 이동한다.")
    @Test
    void loginSuccess() {
        LoginController loginController = new LoginController();

        DataBase.addUser(new User("dean", "password", "", ""));

        HashMap<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("userId", "dean");
        requestBodyMap.put("password", "password");
        RequestBody requestBody = new RequestBody(requestBodyMap);

        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("POST /user/login HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), requestBody);

        HttpResponse httpResponse = new HttpResponse(httpRequest);

        loginController.service(httpRequest, httpResponse);

        assertThat(httpResponse.getLocation()).isEqualTo("/index.html");
    }

    @DisplayName("로그인 실패시 login_failed 페이지로 이동한다.")
    @Test
    void loginFail() {
        LoginController loginController = new LoginController();

        DataBase.addUser(new User("dean", "password", "", ""));

        HashMap<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("userId", "dean");
        requestBodyMap.put("password", "password2");
        RequestBody requestBody = new RequestBody(requestBodyMap);

        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("POST /user/login HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), requestBody);

        HttpResponse httpResponse = new HttpResponse(httpRequest);

        loginController.service(httpRequest, httpResponse);

        assertThat(httpResponse.getLocation()).isEqualTo("/user/login_failed.html");
    }
}
