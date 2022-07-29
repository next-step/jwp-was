package webserver.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.JsonUtils;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.request.RequestBodyTest;
import webserver.response.HttpResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandlerMapperTest {
    @Test
    @DisplayName("유저 생성 요청이 주어졌을 때 user controller로 잘 요청한다.")
    void testUserControllerMapping() throws Exception {
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


        HttpResponse result = new HandlerMapper().handle(httpRequest);

        assertThat(result.getBody())
                .containsExactly(JsonUtils.convertObjectToJsonString(user).getBytes(StandardCharsets.UTF_8));
    }
}
