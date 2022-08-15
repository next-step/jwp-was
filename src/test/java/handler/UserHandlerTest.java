package handler;

import db.DataBase;
import model.User;
import model.request.HttpRequestMessage;
import model.response.HttpResponseMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static dummy.HttpRequestHeaderDummy.GET_USER_HTTP_REQUEST_DUMMY;
import static dummy.HttpRequestHeaderDummy.HTTP_POST_LOGIN_REQUEST_HEADER_STRING_DUMMY;
import static org.assertj.core.api.Assertions.assertThat;

class UserHandlerTest {
    @Test
    @DisplayName("/user/ 경로의 path를 가진 HttpRequestHeader는 true를 반환한다.")
    void canHandleTest() {
        String userPath = "/user/something";
        HttpRequestMessage getUserHttpRequest = GET_USER_HTTP_REQUEST_DUMMY;
        AbstractHandler userHandler = new UserHandler();

        Boolean result = userHandler.canHandling(getUserHttpRequest);

        assertThat(result).isTrue();
        assertThat(GET_USER_HTTP_REQUEST_DUMMY.getPath()).isEqualTo(userPath);
    }

    @Test
    @DisplayName("세션이 없는 login요청이 들어오면 세션을 생성해 쿠키에 저장한다.")
    void loginWithOutSessionTest() throws IOException {
        String httpPostRequestHeaderStringDummy = HTTP_POST_LOGIN_REQUEST_HEADER_STRING_DUMMY;
        User loginUser = new User("javajigi", "password", "", "");
        DataBase.addUser(loginUser);

        InputStream inputStream = new ByteArrayInputStream(httpPostRequestHeaderStringDummy.getBytes());

        HttpRequestMessage httpRequestMessage = HttpRequestMessage.from(inputStream);
        UserHandler userHandler = new UserHandler();
        HttpResponseMessage httpResponseMessage = userHandler.handle(httpRequestMessage);

        Optional<String> result = httpResponseMessage.getHttpHeaders().stream().filter(it -> it.contains("JSESSIONID")).findFirst();

        assertThat(result.isPresent()).isTrue();
    }
}
