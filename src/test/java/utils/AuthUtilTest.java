package utils;


import db.DataBase;
import http.httprequest.HttpRequest;
import http.httprequest.requestbody.RequestBody;
import http.httprequest.requestheader.RequestHeader;
import http.httprequest.requestline.RequestLine;
import http.httpresponse.HttpResponse;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static http.httpresponse.HttpHeaders.SET_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;

class AuthUtilTest {

    private final static User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
    @BeforeAll
    static void beforeAll() {
        DataBase.addUser(user);
    }

    @AfterAll
    static void afterAll() {
        DataBase.deleteUser(user);
    }

    @Test
    @DisplayName("로그인")
    void login() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.from("GET /index.html HTTP/1.1"),
                RequestHeader.from(List.of("Host: localhost:8090")),
                new RequestBody(Map.of("userId", "javajigi", "password", "password", "name", "자바지기", "email", "javajigi@gmail.com"))
        );

        HttpResponse httpResponse = AuthUtil.login(httpRequest);

        assertThat(httpResponse.getResponseHeader().getHeaderEntries())
                .contains(Map.entry(SET_COOKIE, "logined=true; Path=/"));

    }
}
