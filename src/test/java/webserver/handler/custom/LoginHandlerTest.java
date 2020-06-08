package webserver.handler.custom;

import db.DataBase;
import http.request.*;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.Response;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginHandlerTest {
    @DisplayName("work() 호출 - DB 정보와 아이디/이메일 일치")
    @Test
    void workWhenValidUser() throws IOException, URISyntaxException {
        //given
        String requestLine = "POST /login HTTP/1.1";
        Headers headers = new Headers(new HashMap<>());
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        DataBase.addUser(User.of(new QueryStrings(QueryStrings.parseQueryStrings(body))));
        Request request = new Request(new RequestLine(requestLine), headers, new RequestBody(body));
        LoginHandler handler = new LoginHandler("/login");

        //when
        Response response = handler.work(request);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getContentType()).isEqualTo(ContentType.HTML);
        assertThat(response.getHeaderByKey("Set-Cookie")).isEqualTo("logined=true; Path=/; ");
        assertThat(response.getBody())
                .isEqualTo(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
    }

    @DisplayName("work() 호출 - DB와 아이디/이메일 불일치")
    @Test
    void workWhenInvalidUser() throws IOException, URISyntaxException {
        //given
        String requestLine = "POST /login HTTP/1.1";
        Headers headers = new Headers(new HashMap<>());
        String body = "userId=javajigi&password=password2222&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Request request = new Request(new RequestLine(requestLine), headers, new RequestBody(body));
        LoginHandler handler = new LoginHandler("/login");

        //when
        Response response = handler.work(request);

        //then
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.FOUND);
        assertThat(response.getContentType())
                .isEqualTo(ContentType.HTML);
        assertThat(response.getHeaderByKey("Set-Cookie"))
                .isEqualTo("logined=false; Path=; ");
        assertThat(response.getBody())
                .isEqualTo(FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html"));
    }
}
