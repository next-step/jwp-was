package actions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import actions.user.UserCreateAction;
import actions.user.UserListAction;
import db.DataBase;
import model.User;
import webserver.handler.ModelView;
import webserver.http.HttpBaseRequest;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolvers.body.FormBodyResolver;

public class UserListActionTest {

	private static final String HTTP_SIGNUP_POST = "POST /user/create HTTP/1.1\n" +
			"Host: localhost:8080\n" +
			"Connection: keep-alive\n" +
			"Content-Length: 93\n" +
			"Content-Type: application/x-www-form-urlencoded\n" +
			"Accept: */*\n" +
			"\n" +
			"userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
	
	private static final String HTTP_LIST_GET = "GET /user/list HTTP/1.1\n" +
			"Host: localhost:8080\n" +
			"Connection: keep-alive\n" +
			"Content-Length: 33\n" +
			"Content-Type: application/x-www-form-urlencoded\n" +
			"Cookie: logined=true\n" +
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

	@DisplayName("POST /user/list 요청 attribute 정보 확인")
	@Test
	void sign_in() throws IOException {
		FormBodyResolver formBodyResolver = FormBodyResolver.getInstance();
		HttpRequest request = formBodyResolver.resolve(HttpBaseRequest.parse(new ByteArrayInputStream(HTTP_LIST_GET.getBytes())));
		HttpResponse response = HttpResponse.of(request, new DataOutputStream(new ByteArrayOutputStream()));
		UserListAction handler = new UserListAction();
		handler.actionHandle(request, response);
		
		ModelView modelView = request.getModelView();
		Collection<User> users = (Collection<User>)modelView.getModel().get("users");
		assertThat(modelView.getView()).isEqualTo("user/list");
		assertThat(users).containsExactlyElementsOf(DataBase.findAll());
		assertThat(users.contains(DataBase.findUserById("javajigi"))).isTrue();
	}

}
