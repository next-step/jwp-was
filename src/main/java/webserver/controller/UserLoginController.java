package webserver.controller;

import webserver.exception.InvalidPasswordException;
import webserver.exception.NotFoundUserException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.service.UserService;

public class UserLoginController extends AbstractController {

	private final UserService userService;

	public UserLoginController() {
		userService = new UserService();
	}

	@Override
	protected HttpResponse doGet(HttpRequest httpRequest) {
		if (httpRequest.isLogin()) {
			return doRedirect(httpRequest, "/index.html");
		}

		return super.doGet(httpRequest);
	}

	@Override
	HttpResponse doPost(HttpRequest httpRequest) {
		try {
			userService.login(httpRequest.getHttpBody());
			return doRedirect(httpRequest, "/index.html", "logined=true; Path=/");
		} catch (InvalidPasswordException | NotFoundUserException e) {
			return doRedirect(httpRequest, "/user/login_failed.html", "logined=false; Path=/");
		}
	}
}
