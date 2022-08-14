package webserver.handler;

import db.DataBase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.ModelAndView;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;
import webserver.http.RequestLine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


class ListMemberHandlerTest {

    @BeforeEach
    void setup() {
        DataBase.clear();
    }

    @DisplayName("로그인 하지 않은 사용자가 접근하면 login.html 로 리다이렉트 시켜야 한다.")
    @Test
    void redirectLoginPageTest() {
        // given
        ListMemberHandler listMemberHandler = new ListMemberHandler((req, res) -> new HttpSession("TEST"));
        HttpRequest httpRequest = new HttpRequest(RequestLine.parseOf("GET /user/list HTTP/1.1"));
        HttpResponse httpResponse = new HttpResponse();

        // when
        ModelAndView modelAndView = listMemberHandler.handle(httpRequest, httpResponse);

        // then
        Assertions.assertThat(modelAndView.getView()).isEqualTo("redirect:/user/login.html");
    }

    @DisplayName("로그인 사용자가 접근하면 사용자 리스트 화면이 보여야 한다.")
    @Test
    void loginListPageTest() {
        // given
        HttpSession httpSession = new HttpSession("TEST");
        httpSession.setAttribute("logined", true);
        ListMemberHandler listMemberHandler = new ListMemberHandler((req, res) -> httpSession);
        HttpRequest httpRequest = new HttpRequest(RequestLine.parseOf("GET /user/list HTTP/1.1"));
        HttpResponse httpResponse = new HttpResponse();
        List<User> currentUsers = addUsers();

        // when
        ModelAndView modelAndView = listMemberHandler.handle(httpRequest, httpResponse);

        // then
        Assertions.assertThat(modelAndView.getView()).isEqualTo("/user/list");
        Assertions.assertThat(modelAndView.getModel()).containsKey("users");
        Collection<User> users = (Collection<User>) modelAndView.getModel().get("users");
        Assertions.assertThat(users.toArray()).containsExactlyInAnyOrder(currentUsers.toArray());
    }

    private List<User> addUsers() {
        User user1 = new User("id1", "", "", "");
        User user2 = new User("id2", "", "", "");
        User user3 = new User("id3", "", "", "");

        DataBase.addUser(user1);
        DataBase.addUser(user2);
        DataBase.addUser(user3);

        ArrayList<User> objects = new ArrayList<>();
        objects.add(user1);
        objects.add(user2);
        objects.add(user3);

        return objects;
    }

}
