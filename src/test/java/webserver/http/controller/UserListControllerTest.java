package webserver.http.controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Header;
import webserver.http.HttpSession;
import webserver.http.HttpSessionManager;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestLine;
import webserver.http.Cookie;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseThreadLocal;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {

    @DisplayName("로그인이 되어있지 않으면 /user/login.html로 이동한다.")
    @Test
    void notLogin() {
        UserListController userListController = new UserListController();
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /user/list HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyList()), null);

        HttpResponse httpResponse = new HttpResponse(httpRequest);
        HttpResponseThreadLocal.threadLocal.set(httpResponse);

        userListController.service(httpRequest, httpResponse);

        assertThat(httpResponse.getLocation()).isEqualTo("/user/login.html");
    }

    @DisplayName("로그인이 되어 있는 경우 user 목록을 출력한다.")
    @Test
    void doService() {
        UserListController userListController = new UserListController();

        DataBase.addUser(new User("dean", "password", "dongchul", "dean@naver.com"));

        HttpSessionManager.setSession(new HttpSession("jsessionId"));

        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /user/list HTTP/1.1"),
                new Header(Collections.emptyMap(), List.of(Cookie.of("JSESSIONID", "jsessionId"))), null);
        httpRequest.getSession().setAttribute("logined", true);

        HttpResponse httpResponse = new HttpResponse(httpRequest);
        HttpResponseThreadLocal.threadLocal.set(httpResponse);

        userListController.service(httpRequest, httpResponse);

        assertThat(new String(httpResponse.getBody())).contains("<th scope=\"row\">1</th> <td>dean</td> <td>dongchul</td> <td>dean@naver.com</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
    }
}
