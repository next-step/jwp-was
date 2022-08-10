package webserver.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.Fixtures;
import utils.JsonUtils;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.response.HttpResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class HandlerMapperTest {
    @Test
    @DisplayName("유저 생성 요청이 주어졌을 때 user controller로 잘 요청한다.")
    void testUserControllerMapping() throws Exception {
        HttpRequest httpRequest = Fixtures.createHttpRequest("logined=true");
        httpRequest.setBody(Fixtures.createRequestBody());
        User user = Fixtures.createUser();

        HttpResponse result = new HandlerMapper().handle(httpRequest);

        assertThat(result.getBody())
                .containsExactly(JsonUtils.convertObjectToJsonString(user).getBytes(StandardCharsets.UTF_8));
    }
}
