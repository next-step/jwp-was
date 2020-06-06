package webserver.handler.custom;

import db.DataBase;
import http.request.Request;
import http.request.RequestBody;
import http.request.Headers;
import http.request.RequestLine;
import http.response.Response;
import http.response.ContentType;
import http.response.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateHandlerTest {
    @DisplayName("work메소드를 호출하면, DB에 user가 저장된 후 index.html로 이동")
    @Test
    void work() throws IOException, URISyntaxException {
        //given
        String requestLine = "POST /users HTTP/1.1";
        Headers headers = new Headers(new HashMap<>());
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Request request = new Request(new RequestLine(requestLine), headers, new RequestBody(body));
        UserCreateHandler handler = new UserCreateHandler("/users");

        //when
        Response response = handler.work(request);

        //then
        assertThat(DataBase.findUserById("javajigi").getPassword()).isEqualTo("password");
        assertThat(response.getContentType()).isEqualTo(ContentType.HTML);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody())
                .isEqualTo(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
    }
}
