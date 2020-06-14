package controller.user;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateControllerTest extends IntegrationControllerTest {

    private UserCreateController userCreateController = new UserCreateController();

    @DisplayName("User 생성하기")
    @Test
    void userCreate() throws IOException {
        /* given */
        HttpRequest httpRequest = createHttpRequestFrom("/user/User_Create_Test.txt");
        ByteArrayOutputStream outputStream = createByteArrayOutputStream();

        /* when */
        HttpResponse httpResponse = userCreateController.service(httpRequest);
        httpResponse.flush(outputStream);

        /* then */
        String result = toStringFrom(outputStream);
        assertThat(result).contains("HTTP/1.1 302 FOUND");
        assertThat(result).contains("Location: /index.html");
    }
}
