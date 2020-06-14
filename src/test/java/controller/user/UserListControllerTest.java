package controller.user;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest extends IntegrationControllerTest {

    private UserListController userListController = new UserListController();

    @DisplayName("로그인 하지 않은 상태에서 User List 조회하기")
    @Test
    void userListWithoutLogin() throws IOException {
        /* given */
        HttpRequest httpRequest = createHttpRequestFrom("/user/User_List_Without_Login_Test.txt");
        ByteArrayOutputStream outputStream = createByteArrayOutputStream();

        /* when */
        HttpResponse httpResponse = userListController.service(httpRequest);
        httpResponse.flush(outputStream);

        /* then */
        String result = toStringFrom(outputStream);
        assertThat(result).contains("HTTP/1.1 302 FOUND");
        assertThat(result).contains("Location: /user/login.html");
    }

    @DisplayName("로그인 한 상태에서 User List 조회하기")
    @Test
    void userListWithLogin() throws IOException {
        /* given */
        HttpRequest httpRequest = createHttpRequestFrom("/user/User_List_With_Login_Test.txt");
        ByteArrayOutputStream outputStream = createByteArrayOutputStream();

        /* when */
        HttpResponse httpResponse = userListController.service(httpRequest);
        httpResponse.flush(outputStream);

        /* then */
        String result = toStringFrom(outputStream);
        assertThat(result).contains("HTTP/1.1 200 OK");
    }
}
