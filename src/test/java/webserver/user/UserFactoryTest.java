package webserver.user;

import model.User;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.util.HttpRequestUtil;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class UserFactoryTest {

    @Test
    void create_with_valid_http_post_request() throws UnsupportedEncodingException {
        String stringPath = "/user/create";
        String stringBody = "userId=javajigi&password=password&name=javajigi&email=javajigi@slipp.net";
        HttpRequest httpRequest = HttpRequestUtil.create_with_post_request(stringPath, stringBody);

        User actual = UserFactory.from(httpRequest);

        assertThat(actual.getUserId()).isEqualTo("javajigi");
        assertThat(actual.getPassword()).isEqualTo("password");
        assertThat(actual.getName()).isEqualTo("javajigi");
        assertThat(actual.getEmail()).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void create_with_valid_http_get_request() throws UnsupportedEncodingException {
        String stringPath = "/user/create?userId=javajigi&password=password&name=javajigi&email=javajigi@slipp.net";
        HttpRequest httpRequest = HttpRequestUtil.create_with_get_request(stringPath);

        User actual = UserFactory.from(httpRequest);

        assertThat(actual.getUserId()).isEqualTo("javajigi");
        assertThat(actual.getPassword()).isEqualTo("password");
        assertThat(actual.getName()).isEqualTo("javajigi");
        assertThat(actual.getEmail()).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void create_with_null_http_request() {
        final HttpRequest httpRequest = null;

        assertThatCode(() -> UserFactory.from(httpRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
