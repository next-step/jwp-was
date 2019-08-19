package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import webserver.http.request.HttpRequestParams;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @DisplayName("생성 - requestParams")
    @ParameterizedTest
    @CsvSource({"userId=123&password=123&name=123&email=123,123"})
    void of(String paramString, String expected) {
        //given
        HttpRequestParams requestParams = HttpRequestParams.of(paramString);

        //when
        User user = User.of(requestParams);

        //then
        assertThat(user.getUserId()).isEqualTo(expected);
        assertThat(user.getPassword()).isEqualTo(expected);
        assertThat(user.getName()).isEqualTo(expected);
        assertThat(user.getEmail()).isEqualTo(expected);
    }

    @DisplayName("빈 객체 생성")
    @Test
    void empty() {
        //when
        User user = User.empty();

        //then
        assertThat(user.getUserId()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.getName()).isNull();
        assertThat(user.getEmail()).isNull();
    }

    @DisplayName("user정보 확인")
    @ParameterizedTest
    @CsvSource({"userId=123&password=123,true"
            , "userId=123,password=432,false"})
    void isOwner(String params, boolean expected) {
        //given
        HttpRequestParams userInfo = HttpRequestParams.of("userId=123&password=123");
        HttpRequestParams loginInfo = HttpRequestParams.of(params);

        //when
        User user = User.of(userInfo);

        //then
        assertThat(user.isOwner(loginInfo)).isEqualTo(expected);
    }

    @DisplayName("회원정보가 없는경우")
    @Test
    void isOwner() {
        //given
        HttpRequestParams loginInfo = HttpRequestParams.of("userId=123&password=123");

        //when
        User user = User.empty();

        //then
        assertThat(user.isOwner(loginInfo)).isFalse();
    }
}