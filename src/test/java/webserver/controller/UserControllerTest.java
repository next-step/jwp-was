package webserver.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    @Test
    @DisplayName("/user/create 요청이 들어오면 param을 잘 파싱해 user에 저장한다.")
    void createUserTest() throws Exception {
        HttpRequest httpRequest = new HttpRequest(
                List.of(("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*").split("\n"))
        );

        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");
        HttpResponse result = new UserController().execute(httpRequest);

        assertThat(result.getBody()).containsExactly(JsonUtils.convertObjectToJsonString(user).getBytes(StandardCharsets.UTF_8));
    }
}
