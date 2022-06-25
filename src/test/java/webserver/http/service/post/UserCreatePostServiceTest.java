package webserver.http.service.post;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestLine;
import webserver.http.response.HttpResponse;

import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreatePostServiceTest {
    @DisplayName("POST /user/create 요청을 처리할수 있다.")
    @Test
    void pathMatch() {
        UserCreatePostService userCreatePostService = new UserCreatePostService();
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("POST /user/create HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);

        assertThat(userCreatePostService.pathMatch(httpRequest)).isTrue();
    }

    @DisplayName("유저를 생성할수 있다.")
    @Test
    void createUser() {
        UserCreatePostService userCreatePostService = new UserCreatePostService();

        HashMap<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("userId", "dean");
        requestBodyMap.put("password", "password");
        requestBodyMap.put("name", "dongchul");
        requestBodyMap.put("email", "dean@naver.com");
        RequestBody requestBody = new RequestBody(requestBodyMap);

        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("POST /user/create HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), requestBody);

        userCreatePostService.doService(httpRequest, new HttpResponse(httpRequest));

        User user = DataBase.findUserById("dean");

        assertThat(user.getName()).isEqualTo("dongchul");
        assertThat(user.getEmail()).isEqualTo("dean@naver.com");
    }
}
