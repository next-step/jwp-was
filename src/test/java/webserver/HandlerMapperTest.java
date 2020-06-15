package webserver;

import http.Header;
import http.RequestLine;
import http.RequestMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.handler.*;


import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerMapperTest {

    @DisplayName("/user/create uri로 회원가입 핸들러 얻기")
    @Test
    void test_getUserCreateHandler_should_pass() {
        // given
        RequestMessage request = RequestMessage.createWithDefaultBody(
                RequestLine.from("POST /user/create HTTP/1.1"),
                new Header(Collections.emptyList())
        );
        // when
        Handler handler = HandlerMapper.getHandler(request);
        // then
        assertThat(handler).isInstanceOf(UserCreateHandler.class);
    }

    @DisplayName("/user/login 처리 핸들러 얻기")
    @Test
    void test_getUserLoginHandler_withUri_should_pass() {
        // given
        RequestMessage request = RequestMessage.createWithDefaultBody(
                RequestLine.from("GET /user/login HTTP/1.1"),
                new Header(Collections.emptyList())
        );
        // when
        Handler handler = HandlerMapper.getHandler(request);
        // then
        assertThat(handler).isInstanceOf(UserLoginHandler.class);
    }

    @DisplayName("/user/list 처리 핸들러 얻기")
    @Test
    void test_getUserListHandler_withUri_should_pass() {
        // given
        RequestMessage request = RequestMessage.createWithDefaultBody(
                RequestLine.from("GET /user/list HTTP/1.1"),
                new Header(Collections.emptyList())
        );
        // when
        Handler handler = HandlerMapper.getHandler(request);
        // then
        assertThat(handler).isInstanceOf(UserListHandler.class);
    }

    @DisplayName("상기 외의 uri에 대해서는 디폴트 핸들러 반환")
    @Test
    void test_getDefaultHandler_withUri_should_pass() {
        // given
        RequestMessage request = RequestMessage.createWithDefaultBody(
                RequestLine.from("GET /index.html HTTP/1.1"),
                new Header(Collections.emptyList())
        );
        // when
        Handler handler = HandlerMapper.getHandler(request);
        // then
        assertThat(handler).isInstanceOf(DefaultHandler.class);
    }
}
