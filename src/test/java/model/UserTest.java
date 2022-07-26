package model;

import org.junit.jupiter.api.Test;
import webserver.RequestLine;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void RequestParams_으로_유저_생성테스트() throws UnsupportedEncodingException {

        final String request = "GET /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net/ HTTP/1.1";

        final RequestLine requestLine = new RequestLine(request);
        final User user = User.createUser(requestLine.getRequestParams());

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getName()).isEqualTo("박재성");
    }

}