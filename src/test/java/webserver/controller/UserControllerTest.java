package webserver.controller;

import java.util.List;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    @Test
    @DisplayName("/user/create 요청이 들어오면 param을 잘 파싱해 user에 저장한다.")
    void createUserTest() {
        HttpRequest httpRequest = new HttpRequest(
                List.of(("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*").split("\n"))
        );

        User result = new UserController().execute(httpRequest);

        assertEquals(result.getUserId(), "javajigi");
        assertEquals(result.getPassword(), "password");
        assertEquals(result.getName(), "박재성");
        assertEquals(result.getEmail(), "javajigi@slipp.net");
    }
}
