package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import webserver.domain.RequestLine;

class GetUserFormHtmlResponseTest {

    @DisplayName("유저 생성")
    @Test
    void createUser() {
        // given
        GetUserFormHtmlResponse response = new GetUserFormHtmlResponse();
        RequestLine requestLine = RequestLine.create("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");

        // when
        response.response(requestLine);

        // then
        assertThat(DataBase.findUserById("javajigi")).isNotNull();
    }
}
