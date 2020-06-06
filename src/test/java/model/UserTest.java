package model;

import http.request.QueryStrings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @DisplayName("QueryStrings를 인자로 받아서 User 생성하기")
    @Test
    void createByQueryStrings() throws UnsupportedEncodingException {
        //given
        String path = "/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        QueryStrings queryStrings = new QueryStrings(path);

        //when
        User user = User.of(queryStrings);

        //then
        assertThat(user).isEqualTo(new User("javajigi", "password",
                URLDecoder.decode("%EB%B0%95%EC%9E%AC%EC%84%B1", "UTF-8"),
                URLDecoder.decode("javajigi%40slipp.net", "UTF-8")));
    }
}