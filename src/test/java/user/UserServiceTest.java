package user;

import db.DataBase;
import http.HeaderProperty;
import http.HttpHeaders;
import http.HttpRequest;
import http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.HttpStringBuilder;

import java.io.*;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class UserServiceTest {

    private User user;
    private UserService userService;

    @BeforeEach
    public void signUp() {
        String userId = "KingCjy";
        String password = "1234";

        user = new User(userId, password, "최재용", "tlssycks@gmail.com");
        userService = new UserService();
        userService.signUp(userId, password, "최재용", "tlssycks@gmail.com");
    }

    @Test
    public void signUpTest() {
        assertThat(DataBase.findAll()).contains(user);
    }

    @Test
    public void loginTest() {
        String userId = "KingCjy";
        String password = "1234";
        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(new ByteArrayOutputStream()));

        boolean login = userService.login(httpResponse, userId, password);

        assertThat(login).isTrue();
    }

    @Test
    @DisplayName("로그인 여부 확인")
    public void isLoggedInTest() throws IOException {
        String httpString = HttpStringBuilder.builder()
                .addHeader(HeaderProperty.COOKIE.getValue(), "logined=true")
                .buildRequest();

        HttpRequest httpRequest = HttpRequest.from(new BufferedReader(new StringReader(httpString)));
        boolean isLoggedIn = userService.isLoggedIn(httpRequest);

        assertThat(isLoggedIn).isTrue();
    }

    @Test
    public void findAllUsersTest() {
        Collection<User> users = userService.findAllUsers();

        assertThat(users).contains(user);
    }
}
