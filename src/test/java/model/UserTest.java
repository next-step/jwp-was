package model;

import http.QueryString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @DisplayName("쿼리스트링으로 User 객체 생성")
    @Test
    void test_createUser_with_queryString_should_pass() {
        // given
        QueryString queryString = new QueryString("userId=crystal&password=password&name=%EC%9E%84%EC%88%98%EC%A0%95&email=crystal%40naver.com");
        // when
        User user = new User(queryString);
        // then
        assertThat(user.equals(new User("crystal", "password", "임수정", "crystal.@naver.com")));
    }

}