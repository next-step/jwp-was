package model;

import org.junit.jupiter.api.Test;
import webserver.RequestLine;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void body_로_유저_생성테스트() throws UnsupportedEncodingException {
        final String data = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final Body body = new Body(data);

        final User user = User.createUser(body);

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getName()).isEqualTo("박재성");
        assertThat(user.getEmail()).isEqualTo("javajigi@slipp.net");

    }

}