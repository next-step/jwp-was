package http;

import model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestPathTest {

    @Test
    void parsePath() {
        String path = "GET /user/create?userId=rimeorange&password=dkagh12&name=%EC%8B%AC%EC%9B%90%EB%B3%B4&email=simwb%40hanmail.net HTTP/1.1";

        RequestLine requestLine =RequestLineParser.parse(path);

        String userId = requestLine.getQueryString().getParameter("userId");
        String password = requestLine.getQueryString().getParameter("password");
        String name = requestLine.getQueryString().getParameter("name");
        String email = requestLine.getQueryString().getParameter("email");

        User user = new User(userId, password, name, email);

        assertThat(user.getUserId()).isEqualTo("rimeorange");
        assertThat(user.getPassword()).isEqualTo("dkagh12");
        assertThat(user.getName()).isEqualTo("심원보");
        assertThat(user.getEmail()).isEqualTo("simwb@hanmail.net");
    }

}
