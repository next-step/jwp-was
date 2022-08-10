package webserver.controller;

import enums.HttpStatusCode;
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
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCreateControllerTest {
    @Test
    @DisplayName("/user/create 요청이 들어오면 body를 잘 파싱해 user에 저장하고 /index.html로 redirect한다.")
    void createUserTest() throws Exception {
        HttpRequest httpRequest = Fixtures.createHttpRequest("logined=true");
        httpRequest.setBody(Fixtures.createRequestBody());

        User user = Fixtures.createUser();
        HttpResponse result = new UserCreateController().execute(httpRequest);

        assertEquals(result.getStatusCode(), HttpStatusCode.FOUND);
        assertEquals(result.getHeaders().getHeader("Location"), "/index.html");
        assertArrayEquals(result.getBody(), JsonUtils.convertObjectToJsonString(user).getBytes(StandardCharsets.UTF_8));
    }
}
