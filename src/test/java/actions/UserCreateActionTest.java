package actions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import actions.user.UserCreateAction;
import db.DataBase;
import webserver.http.HttpBaseRequest;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolvers.body.FormBodyResolver;

public class UserCreateActionTest {
	
	private static final String HTTP_PLAIN_POST = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 93\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
	
	@Test
    @DisplayName("POST /user/create 요청을 통해 유저의 정보를 받아 회원가입을 한다.")
    void sign_up() throws IOException {
		FormBodyResolver formBodyResolver = FormBodyResolver.getInstance();
        HttpRequest request = formBodyResolver.resolve(HttpBaseRequest.parse(new ByteArrayInputStream(HTTP_PLAIN_POST.getBytes())));
        UserCreateAction handler = new UserCreateAction();
        handler.actionHandle(request, HttpResponse.of(request, new DataOutputStream(new ByteArrayOutputStream())));
        assertThat(DataBase.findUserById("javajigi").getName()).isEqualTo("박재성");
    }

}
