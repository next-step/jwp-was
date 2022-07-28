package webserver.response.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import model.UserException;

class PostUserCreateResponseTest {

    @DisplayName("User를 생성하여 저장한다.")
    @Test
    void create() {
        // given
        PostUserCreateResponse response = new PostUserCreateResponse();

        // when
        response.response("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        User actual = DataBase.findUserById("javajigi");

        // then
        assertThat(actual.getUserId()).isEqualTo("javajigi");
        assertThat(actual.getPassword()).isEqualTo("password");
        assertThat(actual.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(actual.getEmail()).isEqualTo("javajigi%40slipp.net");
    }

    @DisplayName("requestBody에 빈 값이 있을 경우 생성이 실패되어야 한다.")
    @Test
    void createFailedUserCreate() {
        PostUserCreateResponse response = new PostUserCreateResponse();

        assertThatThrownBy(() -> response.response(""))
                .isInstanceOf(UserException.class)
                .hasMessage("userInfo가 유효하지 않습니다.");
    }
}