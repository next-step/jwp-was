package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import webserver.request.header.RequestHeader;
import webserver.response.get.GetUserFormHtmlResponse;

class GetUserFormHtmlResponseTest {

    @DisplayName("유저 생성")
    @Test
    void createUser() {
        // given
        GetUserFormHtmlResponse response = new GetUserFormHtmlResponse();

        // when
        response.response(RequestHeader.create("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1"));

        // then
        assertThat(DataBase.findUserById("javajigi")).isNotNull();
    }
}
