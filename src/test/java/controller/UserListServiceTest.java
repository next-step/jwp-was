package controller;

import org.junit.jupiter.api.Test;
import utils.IOUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UserListServiceTest {

    @Test
    void doGet() throws IOException {
        String requestStr = "GET /user/list.html HTTP/1.1";
        HttpRequest httpRequest = HttpRequest.from(IOUtils.toBufferedReader(requestStr));

        UserListController userListService = new UserListController();
        HttpResponse httpResponse = userListService.doGet(httpRequest);

        assertThat(httpResponse.getCode()).isEqualTo("202");
    }
}