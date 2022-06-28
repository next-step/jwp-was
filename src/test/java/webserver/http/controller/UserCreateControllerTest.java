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

class UserCreateControllerTest {

    @DisplayName("Get 요청으로 유저를 생성할수 있다.")
    @Test
    void createUserGet() {
        UserCreateController userCreateController = new UserCreateController();

        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /user/create?userId=dean&password=password&name=dongchul&email=dean@naver.com HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);

        userCreateController.service(httpRequest, new HttpResponse(httpRequest));

        User user = DataBase.findUserById("dean");

        assertThat(user.getName()).isEqualTo("dongchul");
        assertThat(user.getEmail()).isEqualTo("dean@naver.com");
    }

    @DisplayName("Post 요청으로 유저를 생성할수 있다.")
    @Test
    void createUserPost() {
        UserCreateController userCreateController = new UserCreateController();

        HashMap<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("userId", "dean");
        requestBodyMap.put("password", "password");
        requestBodyMap.put("name", "dongchul");
        requestBodyMap.put("email", "dean@naver.com");
        RequestBody requestBody = new RequestBody(requestBodyMap);

        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("POST /user/create HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), requestBody);

        userCreateController.service(httpRequest, new HttpResponse(httpRequest));

        User user = DataBase.findUserById("dean");

        assertThat(user.getName()).isEqualTo("dongchul");
        assertThat(user.getEmail()).isEqualTo("dean@naver.com");
    }
}
