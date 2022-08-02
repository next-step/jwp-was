package webserver.controller;

import enums.HttpStatusCode;
import java.nio.charset.StandardCharsets;
import java.util.List;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCreateControllerTest {
    @Test
    @DisplayName("/user/create 요청이 들어오면 body를 잘 파싱해 user에 저장하고 /index.html로 redirect한다.")
    void createUserTest() throws Exception {
        HttpRequest httpRequest = new HttpRequest(
                List.of(("POST /user/create HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: 59\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "Accept: */*\n"))
        );
        httpRequest.setBody(new RequestBody("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"));

        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");
        HttpResponse result = new UserCreateController().execute(httpRequest);

        assertEquals(result.getStatusCode(), HttpStatusCode.FOUND);
        assertEquals(result.getHeaders().getHeader("Location"), "/index.html");
        assertArrayEquals(result.getBody(), JsonUtils.convertObjectToJsonString(user).getBytes(StandardCharsets.UTF_8));
    }
}
