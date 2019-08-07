package webserver.service;

import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {
    @ParameterizedTest
    @CsvSource({"POST /user/create HTTP/1.1 OK,userId=javajigi&password=password&name=김덕만&email=javajigi%40slipp.net,javajigi,password"})
    void create_user(ArgumentsAccessor argumentsAccessor) {
        //given
        RequestLine requestLine = RequestLine.parse(argumentsAccessor.getString(0));
        requestLine.getPath().addParameters(argumentsAccessor.getString(1));

        //when
        UserService userService = new UserService();
        User user = userService.createUser(requestLine.getPath().getParameters());

        //then
        assertThat(user.getUserId()).isEqualTo(argumentsAccessor.getString(2));
        assertThat(user.getPassword()).isEqualTo(argumentsAccessor.getString(3));
    }

    @Test
    void login_fail() {
        //given
        RequestLine requestLine = RequestLine.parse("POST /user/login HTTP/1.1 OK");
        requestLine.getPath().addParameters("userId=javajigi&password=password");

        //when
        UserService userservice = new UserService();
        String cookie = userservice.login(requestLine, requestLine.getPath().getParameters());

        assertThat(cookie).isEqualTo("Set-Cookie: logined=false;");
    }

    @Test
    void check_userInfo() {
        //given
        RequestLine requestLine = RequestLine.parse("POST /user/login HTTP/1.1 OK");
        requestLine.getPath().addParameters("userId=javajigi&password=password&name=김덕만&email=javajigi%40slipp.net");
        User user = User.newInstance(requestLine.getPath().getParameters());

        //when
        UserService userService = new UserService();
        boolean isOwner = userService.isOwner(requestLine.getPath().getParameters(), user);

        //then
        assertThat(isOwner).isTrue();
    }
}