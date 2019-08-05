package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.view.LoginHandler;
import webserver.view.ResourceHandler;
import webserver.view.UserCreateHandler;
import webserver.view.UserListHandler;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RequestMapperTest {

    @DisplayName("/user/create 요청시 UserCreateHandler를 반환하는지")
    @Test
    void user_create() {
        assertThat(RequestMapper.find("/user/create")).isInstanceOfAny(UserCreateHandler.class);
    }

    @DisplayName("/user/login 요청시 LoginHandler를 반환하는지")
    @Test
    void user_login() {
        assertThat(RequestMapper.find("/user/login")).isInstanceOfAny(LoginHandler.class);
    }

    @DisplayName("/user/list 요청시 UserListHandler를 반환하는지")
    @Test
    void user_list() {
        assertThat(RequestMapper.find("/user/list")).isInstanceOfAny(UserListHandler.class);
    }

    @DisplayName("resource인 경우 ResourceHandler를 반환하는지")
    @Test
    void resource() {
        assertThat(RequestMapper.find("/css")).isInstanceOfAny(ResourceHandler.class);
    }
}