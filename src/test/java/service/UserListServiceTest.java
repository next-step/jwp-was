package service;

import org.junit.jupiter.api.Test;
import webserver.request.RequestLine;
import webserver.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

class UserListServiceTest {

    @Test
    void doGet() {
        RequestLine requestLine = RequestLine.from("GET /user/list.html HTTP/1.1");

        UserListService userListService = new UserListService();
        Response response = userListService.doGet(requestLine);

        assertThat(response.getCode()).isEqualTo("202");
    }
}