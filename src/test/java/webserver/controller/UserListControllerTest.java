package webserver.controller;

import enums.HttpStatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.Fixtures;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserListControllerTest {
    @Test
    @DisplayName("로그인 된 사용자가 유저 조회 요청 시 페이지가 반환된다.")
    void testLoginedUser() throws Exception {
        HttpRequest request = Fixtures.createHttpRequest("logined=true");

        HttpResponse result = new UserListController().execute(request);

        assertEquals(result.getStatusCode(), HttpStatusCode.OK);
    }

    @Test
    @DisplayName("로그인 되지 않은 사용자가 유저 조회 요청 시 401 에러 발생한다.")
    void testNotLoginedUser() throws Exception {
        HttpRequest request = Fixtures.createHttpRequest("logined=false");

        HttpResponse result = new UserListController().execute(request);

        assertEquals(result.getStatusCode(), HttpStatusCode.UNAUTHORIZED);
    }
}
