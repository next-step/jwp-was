package webserver.http.service.get;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestLine;
import webserver.http.response.HttpResponse;
import webserver.http.service.post.UserCreatePostService;

import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateGetServiceTest {

    @DisplayName("Get /user/create 요청을 처리할수 있다.")
    @Test
    void pathMatch() {
        UserCreateGetService userCreateGetService = new UserCreateGetService();
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /user/create HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);

        assertThat(userCreateGetService.pathMatch(httpRequest)).isTrue();
    }

    @DisplayName("유저를 생성할수 있다.")
    @Test
    void createUser() {
        UserCreateGetService userCreateGetService = new UserCreateGetService();

        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /user/create?userId=dean&password=password&name=dongchul&email=dean@naver.com HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);

        userCreateGetService.doService(httpRequest, new HttpResponse(httpRequest));

        User user = DataBase.findUserById("dean");

        assertThat(user.getName()).isEqualTo("dongchul");
        assertThat(user.getEmail()).isEqualTo("dean@naver.com");
    }
}
