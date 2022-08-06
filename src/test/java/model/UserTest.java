package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.model.QueryStrings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @DisplayName("queryString으로 전달받은 Map을 전달하여 User 객체를 생성한다.")
    @Test
    void construct() {
        QueryStrings queryStrings = new QueryStrings("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        User user = new User(queryStrings);
        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(user.getEmail()).isEqualTo("javajigi%40slipp.net");
    }
}