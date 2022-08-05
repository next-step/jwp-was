package handler;

import model.request.HttpRequestMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dummy.HttpRequestHeaderDummy.GET_USER_HTTP_REQUEST_DUMMY;
import static org.assertj.core.api.Assertions.assertThat;

class UserHandlerTest {
    @Test
    @DisplayName("/user/ 경로의 path를 가진 HttpRequestHeader는 true를 반환한다.")
    void canHandleTest() {
        String userPath = "/user/something";
        HttpRequestMessage getUserHttpRequest = GET_USER_HTTP_REQUEST_DUMMY;
        UserHandler userHandler = new UserHandler();

        Boolean result = userHandler.canHandling(getUserHttpRequest);

        assertThat(result).isTrue();
        assertThat(GET_USER_HTTP_REQUEST_DUMMY.getPath()).isEqualTo(userPath);
    }
}
