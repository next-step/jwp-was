package webserver;

import http.request.Request;
import http.request.body.Body;
import http.request.headers.Headers2;
import http.request.requestline.requestLine2.RequestLine2;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.customhandler.UserCreateHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateHandlerTest {
    @DisplayName("wokr메소드를 호출하면, DB에 user가 저장된 후 index.html로 이")
    @Test
    void work() throws IOException, URISyntaxException {
        //given
        String requestLine = "POST /users HTTP/1.1";
        Headers2 headers = new Headers2(new HashMap<>());
        Request request = new Request(new RequestLine2(requestLine), headers, new Body(""));
        UserCreateHandler handler = new UserCreateHandler("/users");

        //when
        Response response = handler.work(request);

        //then
        assertThat(response.getContentType()).isEqualTo(ContentType.HTML);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isEqualTo(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
    }
}
