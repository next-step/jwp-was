package model;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void body_로_유저_생성테스트() throws UnsupportedEncodingException {
        final String data = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final RequestBody body = new RequestBody(data);

        final User user = new User(body.getOneValue("userId"), body.getOneValue("password"), body.getOneValue("name"), body.getOneValue("email"));

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getEmail()).isEqualTo("javajigi@slipp.net");
    }
}