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

import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class UserListServiceTest {

    @DisplayName("Get /user/list 요청을 처리할수 있다.")
    @Test
    void pathMatch() {
        UserListService userListService = new UserListService();
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /user/list HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);

        assertThat(userListService.pathMatch(httpRequest)).isTrue();
    }

    @DisplayName("로그인이 되어있지 않으면 /user/login.html로 이동한다.")
    @Test
    void notLogin() {
        UserListService userListService = new UserListService();
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /user/list HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);

        HttpResponse httpResponse = new HttpResponse(httpRequest);
        userListService.doService(httpRequest, httpResponse);

        assertThat(httpResponse.getLocation()).isEqualTo("/user/login.html");
    }

    @DisplayName("로그인이 되어 있는 경우 user 목록을 출력한다.")
    @Test
    void doService() {
        UserListService userListService = new UserListService();

        DataBase.addUser(new User("dean", "password", "dongchul", "dean@naver.com"));

        HashMap<String, String> cookies = new HashMap<>();
        cookies.put("logined", "true");

        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /user/list HTTP/1.1"),
                new Header(Collections.emptyMap(), cookies), null);
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        userListService.doService(httpRequest, httpResponse);

        assertThat(new String(httpResponse.getBody())).contains("<th scope=\"row\">1</th> <td>dean</td> <td>dongchul</td> <td>dean@naver.com</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
    }
}
