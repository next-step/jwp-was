package actions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.net.HttpHeaders;

import actions.user.UserCreateAction;
import actions.user.UserLoginAction;
import db.DataBase;
import webserver.http.HttpBaseRequest;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolvers.body.FormBodyResolver;

public class UserLoginActionTest {

	private static final String HTTP_SIGNUP_POST = "POST /user/create HTTP/1.1\n" +
			"Host: localhost:8080\n" +
			"Connection: keep-alive\n" +
			"Content-Length: 93\n" +
			"Content-Type: application/x-www-form-urlencoded\n" +
			"Accept: */*\n" +
			"\n" +
			"userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
	
	private static final String HTTP_SIGNIN_POST = "POST /user/login HTTP/1.1\n" +
			"Host: localhost:8080\n" +
			"Connection: keep-alive\n" +
			"Content-Length: 33\n" +
			"Content-Type: application/x-www-form-urlencoded\n" +
			"Accept: */*\n" +
			"\n" +
			"userId=javajigi&password=password";
	
	

	@BeforeEach
	void sign_up() throws IOException {
		FormBodyResolver formBodyResolver = FormBodyResolver.getInstance();
		HttpRequest request = formBodyResolver.resolve(HttpBaseRequest.parse(new ByteArrayInputStream(HTTP_SIGNUP_POST.getBytes())));
		HttpResponse response = HttpResponse.of(request, new DataOutputStream(new ByteArrayOutputStream()));
		UserCreateAction handler = new UserCreateAction();
		handler.actionHandle(request, response);
	}

	@DisplayName("POST /user/login 요청을 통해 로그인 한다.")
	@Test
	void sign_in() throws IOException {
		FormBodyResolver formBodyResolver = FormBodyResolver.getInstance();
		HttpRequest request = formBodyResolver.resolve(HttpBaseRequest.parse(new ByteArrayInputStream(HTTP_SIGNIN_POST.getBytes())));
		HttpResponse response = HttpResponse.of(request, new DataOutputStream(new ByteArrayOutputStream()));
		UserLoginAction handler = new UserLoginAction();
		handler.actionHandle(request, response);
		boolean isLogined = response.getHttpHeaders()
				.getHeaderValue(HttpHeaders.SET_COOKIE)
				.stream()
				.filter(value -> value.contains("logined=true"))
				.findFirst()
				.isPresent();

		assertThat(isLogined).isTrue();
	}

}
